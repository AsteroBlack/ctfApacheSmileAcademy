import { Injectable } from '@angular/core';
import { Observable, ReplaySubject, of } from 'rxjs';
import { map, tap, switchMap, catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';
import { RestClientService } from './rest-client.service';
import * as moment from 'moment';
import { DashboardSectionsDatas, ResponseAPIGetDashboard, ResponseAPIGetDashboardItem, ResponseAPIGetDashboardItemCategory } from 'src/app/admin/dashboard/main/dashboard.model';
import { User } from '../models/user';
import { Period } from 'src/app/shared/date-range-selector/date-range-selector.directive';
import { ResponseTypeNumero, TypeNumeroLabel } from '../models/type-numero.model';
import { TypeNumeroService } from './type-numero.service';
import { UtilitiesService } from './utilities.service';

export interface DashboardPeriod {
  start: string;
  end: string;
}

const periodDateTimeFormat = "DD/MM/YYYY HH:mm:ss"

@Injectable({
  providedIn: "root",
})
export class DashboardService {
  private user: User;
  private typeNumeros: ResponseTypeNumero[];

  private dashboardPeriod: DashboardPeriod = {
    start: moment().format(periodDateTimeFormat),
    end: moment().format(periodDateTimeFormat)
  };


  private defaultDashboardSectionsDatas: DashboardSectionsDatas = {
    omci: {
      uniques: {
        unlocked: {},
        locked: {},
        inMachine: {},
        TOTAL: {}
      },
      TOTALS: {
        unlocked: {},
        locked: {},
        inMachine: {},
        TOTAL: {}
      }
    },
    telco: {
      uniques: {
        unlocked: {},
        locked: {},
        inMachine: {},
        TOTAL: {}
      },
      TOTALS: {
        unlocked: {},
        locked: {},
        inMachine: {},
        TOTAL: {}
      }
    }
  }

  private dashboardSectionsDatas: DashboardSectionsDatas;
  private periodSubject = new ReplaySubject<Period>(1);
  period$ = this.periodSubject.asObservable();

  constructor(private restClient: RestClientService, authServ: AuthService, private typeNumeroService: TypeNumeroService, private utilities: UtilitiesService) {
    this.user = authServ.currentUserValue;
  }

  getDashboardDatas() {
    return this.getTypeNumeros().pipe(
      tap(() => {
        this.dashboardSectionsDatas = JSON.parse(JSON.stringify(this.defaultDashboardSectionsDatas));;
        console.log('reset-dashboardSectionsDatas', this.dashboardSectionsDatas, this.defaultDashboardSectionsDatas)
      }),
      switchMap(() => this.getInfos(this.typeNumeros[0].id).pipe(
        this.handleDashboardRequestOperator(`récupérations infos uniques ${this.typeNumeros[0].libelle}`)
      )),
      tap(response => {
        response.item.forEach(item => {
          this.updateDashboardSectionsDatas("uniques", this.typeNumeros[0].libelle, item);
        })
      }),
      switchMap(() => this.getInfos(this.typeNumeros[1].id).pipe(
        this.handleDashboardRequestOperator(`récupérations infos uniques ${this.typeNumeros[1].libelle}`)
      )),
      tap(response => {
        response.item.forEach(item => {
          this.updateDashboardSectionsDatas("uniques", this.typeNumeros[1].libelle, item);
          this.calculTotalKAPIDashboardSectionsDatas();
          this.calculTotalDashboardUnlocked();
          this.calculTotalDashboardSectionLocked();
          this.calculTotalDashboardInMachine();
        })
      }),
      switchMap(() => this.getInfosTotal(this.typeNumeros[0].id).pipe(
        this.handleDashboardRequestOperator(`récupérations infos totals ${this.typeNumeros[0].libelle}`)
      )),
      tap(response => {
        response.item.forEach(item => {
          this.updateDashboardSectionsDatas("TOTALS", this.typeNumeros[0].libelle, item);
        })
      }),
      switchMap(_ => this.getInfosTotal(this.typeNumeros[1].id).pipe(
        this.handleDashboardRequestOperator(`récupérations infos totals ${this.typeNumeros[1].libelle}`)
      )),
      tap(response => {
        response.item.forEach(item => {
          this.updateDashboardSectionsDatas("TOTALS", this.typeNumeros[1].libelle, item);
          this.calculTotalKAPIDashboardSectionsDatas();
          this.calculTotalDashboardUnlocked();
          this.calculTotalDashboardSectionLocked();
          this.calculTotalDashboardInMachine();
        })
      }),
      map(() => {
        return this.dashboardSectionsDatas;
      }),
      catchError(error => {
        alert('cathed')
        this.utilities.showRequestErrorNotification("Echec de la récupération des données", "getting dashboard datas")
        return of(this.defaultDashboardSectionsDatas);
      })
    )
  }

  handleDashboardRequestOperator(resquestName: string) {
    return (observable: Observable<any>) => observable.pipe(
      catchError(error => {
        this.utilities.showRequestErrorNotification(resquestName, "getting dashboard datas")
        return of({ item: [] });
      })
    )
  }

  getTypeNumeros() {
    if (this.typeNumeros === undefined) {
      return this.typeNumeroService.getList().pipe(
        tap(response => {
          if (response.length >= 2) {
            this.typeNumeros = response;
          }
          else {
            throw new Error("less than 2 type numeros finded whereas need at least 2 type numeros for the next requests")
          }
        })
      )
    }
    return of([])
  }

  private updateDashboardSectionsDatas(dashboardSectionDatasSubObjectType: "uniques" | "TOTALS", type: TypeNumeroLabel, responseAPIGetDashboardItem: ResponseAPIGetDashboardItem) {
    //identify dashboard section datas
    let dashboardSectionDatas: "omci" | "telco" | null;
    switch (responseAPIGetDashboardItem.category) {
      case ResponseAPIGetDashboardItemCategory.OMCI: dashboardSectionDatas = "omci";
        break;
      case ResponseAPIGetDashboardItemCategory.TELCO: dashboardSectionDatas = "telco";
        break;
      default: dashboardSectionDatas = null;
    }

    //update dashboardSectionDatas if dashboardSectionDatas identified (is not null)
    if (dashboardSectionDatas) {
      /*this.dashboardSectionsDatas[dashboardSectionDatas][dashboardSectionDatasSubObjectType].unlocked[type] = this.defineKpiDataValue(responseAPIGetDashboardItem.DEBLOQUER);
      this.dashboardSectionsDatas[dashboardSectionDatas][dashboardSectionDatasSubObjectType].locked[type] = this.defineKpiDataValue(responseAPIGetDashboardItem.BLOQUER)
      this.dashboardSectionsDatas[dashboardSectionDatas][dashboardSectionDatasSubObjectType].inMachine[type] = this.defineKpiDataValue(responseAPIGetDashboardItem.MISE_EN_MACHINE);
      */

      //define TOTAL type if his value is already defined or no
      const currentTOTALType = this.dashboardSectionsDatas[dashboardSectionDatas][dashboardSectionDatasSubObjectType].TOTAL[type];
      if (this.checkValidNumber(currentTOTALType)) {
        this.dashboardSectionsDatas[dashboardSectionDatas][dashboardSectionDatasSubObjectType].TOTAL[type] = this.checkValidNumber(responseAPIGetDashboardItem.total) ? currentTOTALType + Number(responseAPIGetDashboardItem.total) : currentTOTALType
      }
      else {
        //this.dashboardSectionsDatas[dashboardSectionDatas][dashboardSectionDatasSubObjectType].TOTAL[type] = this.defineKpiDataValue(responseAPIGetDashboardItem.total);
      }

      //define the TOTAL of both types
      const currentTOTAL = this.dashboardSectionsDatas[dashboardSectionDatas][dashboardSectionDatasSubObjectType].TOTAL;
      if (this.checkValidNumber(currentTOTAL.BSCS) && this.checkValidNumber(currentTOTAL.CORAIL)) {
        this.dashboardSectionsDatas[dashboardSectionDatas][dashboardSectionDatasSubObjectType].TOTAL.TOTAL = currentTOTAL.BSCS + currentTOTAL.CORAIL;
      }
      else if (this.checkValidNumber(currentTOTAL[type])) {
        this.dashboardSectionsDatas[dashboardSectionDatas][dashboardSectionDatasSubObjectType].TOTAL.TOTAL = currentTOTALType;
      }
    }
  }

  private calculTotalKAPIDashboardSectionsDatas() {
    Object.keys(this.dashboardSectionsDatas).forEach(dashboardSectionsDatasKey => {
      Object.keys(this.dashboardSectionsDatas[dashboardSectionsDatasKey]).forEach(dashboardSectionsDatasSubObjectKey => {
        const currentTOTAL = this.dashboardSectionsDatas[dashboardSectionsDatasKey][dashboardSectionsDatasSubObjectKey].TOTAL;
        if (this.checkValidNumber(currentTOTAL.BSCS) && this.checkValidNumber(currentTOTAL.CORAIL)) {
          this.dashboardSectionsDatas[dashboardSectionsDatasKey][dashboardSectionsDatasSubObjectKey].TOTAL.TOTAL = currentTOTAL.BSCS + currentTOTAL.CORAIL;
        }
        else if(!this.checkValidNumber(currentTOTAL.BSCS) && this.checkValidNumber(currentTOTAL.CORAIL)){
          this.dashboardSectionsDatas[dashboardSectionsDatasKey][dashboardSectionsDatasSubObjectKey].TOTAL.TOTAL= currentTOTAL.CORAIL
        }
        else if(this.checkValidNumber(currentTOTAL.BSCS) && !this.checkValidNumber(currentTOTAL.CORAIL)){
          this.dashboardSectionsDatas[dashboardSectionsDatasKey][dashboardSectionsDatasSubObjectKey].TOTAL.TOTAL= currentTOTAL.BSCS
        }else{
          this.dashboardSectionsDatas[dashboardSectionsDatasKey][dashboardSectionsDatasSubObjectKey].TOTAL.TOTAL= 0
        }
      })
    });
  }

  private calculTotalDashboardSectionLocked(){
    Object.keys(this.dashboardSectionsDatas).forEach(dashboardSectionsDatasKey => {
      Object.keys(this.dashboardSectionsDatas[dashboardSectionsDatasKey]).forEach(dashboardSectionsDatasSubObjectKey => {
        const currentTOTAL = this.dashboardSectionsDatas[dashboardSectionsDatasKey][dashboardSectionsDatasSubObjectKey].locked;
        if (this.checkValidNumber(currentTOTAL.BSCS) && this.checkValidNumber(currentTOTAL.CORAIL)) {
          this.dashboardSectionsDatas[dashboardSectionsDatasKey][dashboardSectionsDatasSubObjectKey].locked.TOTAL = currentTOTAL.BSCS + currentTOTAL.CORAIL;
        }
        else if(!this.checkValidNumber(currentTOTAL.BSCS) && this.checkValidNumber(currentTOTAL.CORAIL)){
          this.dashboardSectionsDatas[dashboardSectionsDatasKey][dashboardSectionsDatasSubObjectKey].locked.TOTAL= currentTOTAL.CORAIL
        }
        else if(this.checkValidNumber(currentTOTAL.BSCS) && !this.checkValidNumber(currentTOTAL.CORAIL)){
          this.dashboardSectionsDatas[dashboardSectionsDatasKey][dashboardSectionsDatasSubObjectKey].locked.TOTAL= currentTOTAL.BSCS
        }else{
          this.dashboardSectionsDatas[dashboardSectionsDatasKey][dashboardSectionsDatasSubObjectKey].locked.TOTAL= 0
        }
      })
    });
  }

  private calculTotalDashboardUnlocked(){
    Object.keys(this.dashboardSectionsDatas).forEach(dashboardSectionsDatasKey => {
      Object.keys(this.dashboardSectionsDatas[dashboardSectionsDatasKey]).forEach(dashboardSectionsDatasSubObjectKey => {
        const currentTOTAL = this.dashboardSectionsDatas[dashboardSectionsDatasKey][dashboardSectionsDatasSubObjectKey].unlocked;
        if (this.checkValidNumber(currentTOTAL.BSCS) && this.checkValidNumber(currentTOTAL.CORAIL)) {
          this.dashboardSectionsDatas[dashboardSectionsDatasKey][dashboardSectionsDatasSubObjectKey].unlocked.TOTAL = currentTOTAL.BSCS + currentTOTAL.CORAIL;
        }
        else if(!this.checkValidNumber(currentTOTAL.BSCS) && this.checkValidNumber(currentTOTAL.CORAIL)){
          this.dashboardSectionsDatas[dashboardSectionsDatasKey][dashboardSectionsDatasSubObjectKey].unlocked.TOTAL= currentTOTAL.CORAIL
        }
        else if(this.checkValidNumber(currentTOTAL.BSCS) && !this.checkValidNumber(currentTOTAL.CORAIL)){
          this.dashboardSectionsDatas[dashboardSectionsDatasKey][dashboardSectionsDatasSubObjectKey].unlocked.TOTAL= currentTOTAL.BSCS
        }else{
          this.dashboardSectionsDatas[dashboardSectionsDatasKey][dashboardSectionsDatasSubObjectKey].unlocked.TOTAL= 0
        }
      })
    });
  }

  private calculTotalDashboardInMachine(){
    Object.keys(this.dashboardSectionsDatas).forEach(dashboardSectionsDatasKey => {
      Object.keys(this.dashboardSectionsDatas[dashboardSectionsDatasKey]).forEach(dashboardSectionsDatasSubObjectKey => {
        const currentTOTAL = this.dashboardSectionsDatas[dashboardSectionsDatasKey][dashboardSectionsDatasSubObjectKey].inMachine;
        if (this.checkValidNumber(currentTOTAL.BSCS) && this.checkValidNumber(currentTOTAL.CORAIL)) {
          this.dashboardSectionsDatas[dashboardSectionsDatasKey][dashboardSectionsDatasSubObjectKey].inMachine.TOTAL = currentTOTAL.BSCS + currentTOTAL.CORAIL
        }
        else if(!this.checkValidNumber(currentTOTAL.BSCS) && this.checkValidNumber(currentTOTAL.CORAIL)){
          this.dashboardSectionsDatas[dashboardSectionsDatasKey][dashboardSectionsDatasSubObjectKey].inMachine.TOTAL= currentTOTAL.CORAIL
        }
        else if(this.checkValidNumber(currentTOTAL.BSCS) && !this.checkValidNumber(currentTOTAL.CORAIL)){
          this.dashboardSectionsDatas[dashboardSectionsDatasKey][dashboardSectionsDatasSubObjectKey].inMachine.TOTAL= currentTOTAL.BSCS
        }else{
          this.dashboardSectionsDatas[dashboardSectionsDatasKey][dashboardSectionsDatasSubObjectKey].inMachine.TOTAL= 0
        }
      })
    });
  }

  private defineKpiDataValue(number: string | number | undefined) {
    if (this.checkValidNumber(number)) {
      return +number;
    }

    return undefined;
  }

  private checkValidNumber(number: string | number | undefined) {
    return number != undefined && typeof +number == 'number'
  }

  updatePeriod(period: Period) {
    this.dashboardPeriod = {
      start: moment(period.start).format(periodDateTimeFormat),
      end: moment(period.end).format(periodDateTimeFormat)
    }
    this.periodSubject.next(period)
  }

  getTypeNumero(label: TypeNumeroLabel) {
    return this.typeNumeros.find(typeNumero => typeNumero.libelle === label);
  }

  getInfos(typeNumeroId: number): Observable<ResponseAPIGetDashboard> {
    const req = {
      user: this.user.id,
      data: {
        createdAt: this.dashboardPeriod.start,
        createdAtParam: {
          operator: "[]",
          start: this.dashboardPeriod.start,
          end: this.dashboardPeriod.end
        },
        typeNumeroId
      }
    }

    return this.restClient.post('actionUtilisateur/dashboard', req)
  }

  getInfosTotal(typeNumeroId: number): Observable<ResponseAPIGetDashboard> {
    const req = {
      user: this.user.id,
      data: {
        createdAt: this.dashboardPeriod.start,
        createdAtParam: {
          operator: "[]",
          start: this.dashboardPeriod.start,
          end: this.dashboardPeriod.end
        },
        typeNumeroId
      }
    }

    return this.restClient.post('actionUtilisateur/dashboardLite', req)
  }
}
