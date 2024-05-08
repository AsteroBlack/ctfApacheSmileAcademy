import { Injectable } from '@angular/core';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { Exclusion, ExclusionTime, ExclusionTimePeriod, ExclusionTimeType, RequestBodyParametrageList, RequestBodyUpdateParametrage, RequestBodyUpdateParametrageDataHour, RequestBodyUpdateParametrageDataHourPlage, ResponseExclusion, ResponseParametrageList, ResponseParametrageListItemDataElementHourPlage } from './exclusion.model';
import { User } from 'src/app/core/models/user';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import { AuthService } from 'src/app/core/service/auth.service';
import { Observable, catchError, map, tap, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SmsExclusionsService {
  private readonly featureBasePath = "parametrage";
  private currentUser: User;

  constructor(private restClientService: RestClientService, private utilities: UtilitiesService, private authService: AuthService) {
    this.authService.currentUser.subscribe({
      next: user => {
        this.currentUser = user
      }
    })
  }

  get(): Observable<ResponseExclusion> {
    const datas: RequestBodyParametrageList = {
      user: this.currentUser.id,
      isSimpleLoading: false,
      data: {}
    }
    return this.restClientService.post(`${this.featureBasePath}/getByCriteria`, datas).pipe(
      this.restClientService.responseHasErrorOperator(),
      map((response: ResponseParametrageList): ResponseExclusion => ({
        id: response.items[0].id,
        exclusions: response.items[0].data.datas[0].hours.map(hour => ({
          day: hour.key.charAt(0).toUpperCase() + hour.key.slice(1),
          times: this.fromPlagesToExclusionTimes(hour.plages)
        }))
      })),
      catchError(err => {
        this.utilities.showRequestErrorNotification(err, 'get parametrage list')
        return []
      }),
    )
  }

  update(id: number, exclusions: Exclusion[]) {
    const datas: RequestBodyUpdateParametrage = {
      user: this.currentUser.id,
      datas: [{
        id,
        idTypeParametrage: 1,
        hours: exclusions.map((exclusion): RequestBodyUpdateParametrageDataHour => ({
          key: exclusion.day.toLowerCase(),
          plages: this.convertExclusionTimesToPlageList(exclusion.times)
        }))
      }]
    }
    return this.restClientService.post(`${this.featureBasePath}/update`, datas).pipe(
      this.restClientService.responseHasErrorOperator(),
      catchError(err => {
        this.utilities.showRequestErrorNotification(err, 'update parametrage')
        return throwError(() => err)
      }),
      tap(() => {
        this.utilities.showRequestSuccessNotification('Mis à jour avec succès')
      })
    )
  }

  private fromatHoursArrayToExclusionTimes(hours: number[]) {
    const exclusionTimes: ExclusionTime[] = [];
    hours.sort((a, b) => {
      if (a  > b) {
        return 1;
      }
      return -1;
    });

    for (let i = 0; i < hours.length; i++) {
      const exclusionTimeIdentified = this.identiffyExclusionTimeFromHoursArrayIndex(hours, i);
      const exclusionTimeHour =
        exclusionTimeIdentified.exclusionTimeType === ExclusionTimeType.HOUR ? hours[i] : undefined;
      const exclusionTimePeriod: ExclusionTimePeriod = exclusionTimeIdentified.exclusionTimeType === ExclusionTimeType.PERIOD ?
        {
          startHour: hours[i],
          endHour: hours[exclusionTimeIdentified.endLoopHourIndex] + 1
        } :
        undefined;

      exclusionTimes.push({
        type: exclusionTimeIdentified.exclusionTimeType,
        hour: exclusionTimeHour,
        period: exclusionTimePeriod
      });


      i = exclusionTimeIdentified.endLoopHourIndex;
    }

    return exclusionTimes;
  }

  private fromPlagesToExclusionTimes(plages: ResponseParametrageListItemDataElementHourPlage[]): ExclusionTime[]{
    return plages.map((plage): ExclusionTime=>{
      if(plage.values.length>=2){
        const values = plage.values.sort((a, b) => {
          if (a  > b) {
            return 1;
          }
          return -1;
        });

        //if values of plage has same, so return exclusion of type HOUR else PERIOD
        if(values[0] === values[1]){
          return {
            type: ExclusionTimeType.HOUR,
            hour: plage.values[0]
          }
        }
        return {
          type: ExclusionTimeType.PERIOD,
          period: {startHour: values[0], endHour: values[1]}
        }
      }
      return undefined;
    })
  }

  private identiffyExclusionTimeFromHoursArrayIndex(hours: number[], index: number) {
    let isPeriod = false;
    let previousHourIndex: number;
    let endLoopHourIndex = index;
    for (let i = index; i < hours.length; i++) {
      if (previousHourIndex != undefined) {
        if (hours[i] === hours[previousHourIndex] + 1) {
          isPeriod = true;
          endLoopHourIndex = i;
        } else {
          endLoopHourIndex = previousHourIndex;
          i = hours.length;
        }
      }

      previousHourIndex = i;
    }

    return {
      exclusionTimeType: isPeriod ? ExclusionTimeType.PERIOD : ExclusionTimeType.HOUR,
      endLoopHourIndex
    };
  }

  private convertExclusionTimesToHourList(exclusionTimes: ExclusionTime[]) {
    const hours: number[] = [];

    return exclusionTimes.map(exclusionTime =>
      exclusionTime.type == ExclusionTimeType.HOUR ?
        exclusionTime.hour :
        this.convertExclusionTimePeriodToHourList(exclusionTime.period)
    ).flat();
  }

  private convertExclusionTimesToPlageList(exclusionTimes: ExclusionTime[]): RequestBodyUpdateParametrageDataHourPlage[] {
    const hours: number[] = [];

    return exclusionTimes.map((exclusionTime): RequestBodyUpdateParametrageDataHourPlage => (
      {
        values: exclusionTime.type == ExclusionTimeType.HOUR ?
          [exclusionTime.hour, exclusionTime.hour] :
          this.getHoursOfExclusionTimePeriod(exclusionTime.period)
      }
    ));
  }

  private getHoursOfExclusionTimePeriod(exclusionTimePeriod: ExclusionTimePeriod) {
    return [parseInt(exclusionTimePeriod.startHour.toString()), parseInt(exclusionTimePeriod.endHour.toString())];
  }

  private convertExclusionTimePeriodToHourList(exclusionTimePeriod: ExclusionTimePeriod) {
    const hours: number[] = [];
    for (let i = exclusionTimePeriod.startHour; i < exclusionTimePeriod.endHour; i++) {
      hours.push(i);
    }

    return hours;
  }
}
