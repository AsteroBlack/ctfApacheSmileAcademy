import { SmsExclusionsService } from './sms-exclusions.service';
import { Component, OnInit } from '@angular/core';
import { Subscription, tap } from 'rxjs';
import { Exclusion, ExclusionTimeType, ExclusionTimePeriod } from './exclusion.model';
import { UtilitiesService } from 'src/app/core/service/utilities.service';



@Component({
  selector: 'app-sms-exclusions',
  templateUrl: './sms-exclusions.component.html',
  styleUrls: ['./sms-exclusions.component.scss']
})
export class SmsExclusionsComponent implements OnInit {
  exclusions: Exclusion[] = [];
  exclusionTimeType = ExclusionTimeType;
  exclusionTimesPeriodMinGap = 1;
  idPaarametrage: number;
  performRequest = false;
  days = [
    'Lundi',
    'Mardi',
    'Mercredi',
    'Jeudi',
    'Vendredi',
    'Samedi',
    'Dimanche',
  ];

  hours: number[] = [];

  busyQuery: Subscription;

  constructor(private smsExclusionsService: SmsExclusionsService, private utilities: UtilitiesService) { }

  ngOnInit(): void {
    this.defineDaysList(24)
    this.getExclusions()
  }

  defineDaysList(numberHours: number) {
    for (let x = 1; x <= numberHours; x++) {
      this.hours.push(x)
    }
  }

  addExclusioDay() {
    if (this.days.length > 0 && this.hours.length > 0) {
      this.exclusions.push({
        day: this.getAvailableDaysWithoutInclusion()[0],
        times: [{ type: this.exclusionTimeType.HOUR, hour: this.hours[0] }]
      })
    }
    else {
      this.utilities.showNotification(
        'snackbar-warning',
        `Impossible d'ajouter plus de ${this.days.length} jours`,
        'bottom',
        'center'
      )
    }
  }

  removeExclusionDay(index: number) {
    this.exclusions.splice(index, 1)
  }

  addExclusionTime(exclusionIndex: number) {
    if (this.hours.length > 0) {
      this.exclusions[exclusionIndex].times.push({ type: this.exclusionTimeType.HOUR, hour: this.generateDefaultHour(exclusionIndex) })
    }
    else {
      this.utilities.showNotification(
        'snackbar-warning',
        `Impossible d'ajouter plus de ${this.hours.length} heures`,
        'bottom',
        'center'
      )
    }
  }

  removeExclusionTime(exclusionIndex: number, index: number) {
    this.exclusions[exclusionIndex].times.splice(index, 1);
  }

  /**
   * Return all days which dont already belong to exclusion include day which belong to exclusion at the index (exclusionIndex)
   * @param exclusionIndex
   * @returns
   */
  getAvailableDays(exclusionIndex: number) {
    return this.days.filter(day => this.exclusions[exclusionIndex].day == day ? true : !this.checkDayBelongToExclusion(day))
  }

  getAvailableDaysWithoutInclusion() {
    return this.days.filter(day => !this.checkDayBelongToExclusion(day))
  }

  checkDayBelongToExclusion(day: string) {
    return this.exclusions.some(exclusion => exclusion.day == day)
  }

  /**
   * Return all hours which dont already belong to exclusion include hour at the index (hourIndex)
   * @param exclusionIndex
   * @param timeIndex
   * @returns
   */
  getAvailableHours(exclusionIndex: number, timeIndex) {
    return this.hours.filter(hour => this.exclusions[exclusionIndex].times[timeIndex].hour == hour ? true : !this.checkHourBelongToExclusionTimes(exclusionIndex, hour))
  }

  getAvailableHoursWithoutInclusion(exclusionIndex: number) {
    return this.hours.filter(hour => !this.checkHourBelongToExclusionTimes(exclusionIndex, hour))
  }

  getAvailablePeriodStartHours(exclusionIndex: number, timeIndex: number) {
    const currentPeriodStartHour = this.exclusions[exclusionIndex].times[timeIndex].period.startHour;
    return this.hours.filter(
      hour => currentPeriodStartHour == hour ?
        true :
        (!this.checkHourBelongToExclusionTimesWithTimeExclusion(exclusionIndex, timeIndex, hour) && this.checkPeriodStartHourCanHasEndHourWithTimeExclusion(exclusionIndex, timeIndex, hour))
    )
  }
  getAvailablePeriodStartHoursWithoutInclusion(exclusionIndex: number) {
    return this.hours.filter(
      hour => !this.checkHourBelongToExclusionTimes(exclusionIndex, hour) && this.checkPeriodStartHourCanHasEndHour(exclusionIndex, hour)
    )
  }

  autoDefineGoodPeriodEndHour(exclusionIndex: number, timeIndex: number){
    const currentPeriodEndHour = this.exclusions[exclusionIndex].times[timeIndex].period.endHour;

    if(!this.checkPeriodEndHourAvailable(exclusionIndex, timeIndex, currentPeriodEndHour)){
      const availableEndHours =  this.getAvailablePeriodEndHoursWithoutInclusion(exclusionIndex, timeIndex);
      if(availableEndHours.length>0){
        this.exclusions[exclusionIndex].times[timeIndex].period.endHour = availableEndHours[0];
      }
    }
  }

  getAvailablePeriodEndHours(exclusionIndex: number, timeIndex) {
    const periodEndHour = this.exclusions[exclusionIndex].times[timeIndex].period.endHour
    return this.hours.filter(
      hour => periodEndHour == hour ?
        true :
        (!this.checkHourBelongToExclusionTimesWithTimeExclusion(exclusionIndex, timeIndex, hour) && this.checkPeriodEndHourAvailable(exclusionIndex, timeIndex, hour))
    )
  }

  getAvailablePeriodEndHoursWithoutInclusion(exclusionIndex: number, timeIndex) {
    return this.hours.filter(
      hour => (!this.checkHourBelongToExclusionTimes(exclusionIndex, hour) && this.checkPeriodEndHourAvailable(exclusionIndex, timeIndex, hour))
    )
  }

  checkHourBelongToExclusionTimes(exclusionIndex: number, hour: number) {
    return this.exclusions[exclusionIndex].times.some(exclusionTime =>
      exclusionTime.type == this.exclusionTimeType.PERIOD ? this.checkHourIncludedInExclusionPeriod(hour, exclusionTime.period) : exclusionTime.hour == hour
    )
  }

  checkHourBelongToExclusionTimesWithTimeExclusion(exclusionIndex: number, timeIndex: number, hour: number) {
    return this.exclusions[exclusionIndex].times.some((exclusionTime, index) =>
      index === timeIndex ?
      false :
        exclusionTime.type == this.exclusionTimeType.PERIOD ?
        this.checkHourIncludedInExclusionPeriod(hour, exclusionTime.period) :
        exclusionTime.hour == hour
    )
  }

  checkHourIncludedInExclusionPeriod(hour: number, period: ExclusionTimePeriod) {
    return period.startHour <= hour && hour <= period.endHour;
  }

  checkPeriodStartHourCanHasEndHour(exclusionIndex: number, periodStartHour: number) {
    const periodStartHourPlusGap = periodStartHour + this.exclusionTimesPeriodMinGap;
    return !this.checkHourBelongToExclusionTimes(exclusionIndex, periodStartHourPlusGap) && periodStartHour <= this.hours[this.hours.length - (1 + this.exclusionTimesPeriodMinGap)]
  }


  checkPeriodStartHourCanHasEndHourWithTimeExclusion(exclusionIndex: number, timeIndex: number, periodStartHour: number) {
    const periodStartHourPlusGap = periodStartHour + this.exclusionTimesPeriodMinGap;
    return !this.checkHourBelongToExclusionTimesWithTimeExclusion(exclusionIndex, timeIndex, periodStartHourPlusGap) && periodStartHour <= this.hours[this.hours.length - (1 + this.exclusionTimesPeriodMinGap)]
  }

  checkPeriodEndHourAvailable(exclusionIndex: number, timeIndex: number, periodEndtHour: number) {
    const exclusionTime = this.exclusions[exclusionIndex].times[timeIndex];
    const minStartPeriodStartHourAvailable = periodEndtHour - this.exclusionTimesPeriodMinGap
    if(exclusionTime.period){
      return minStartPeriodStartHourAvailable >= this.exclusions[exclusionIndex].times[timeIndex].period.startHour!
    }

    return periodEndtHour >= this.exclusionTimesPeriodMinGap;
  }

  switchExclusionTimeType(exclusionIndex: number, timeIndex: number) {
    const exclusionTime = this.exclusions[exclusionIndex].times[timeIndex];
    if (exclusionTime.type === this.exclusionTimeType.HOUR) {
      this.exclusions[exclusionIndex].times[timeIndex] = {
        type: this.exclusionTimeType.PERIOD,
        period: this.generateDefaultPeriod(exclusionIndex, timeIndex),
        hour: undefined
      };
    }
    else {
      this.exclusions[exclusionIndex].times[timeIndex] = {
        type: this.exclusionTimeType.HOUR,
        hour: this.generateDefaultHour(exclusionIndex),
        period: undefined
      };
    }
  }

  generateDefaultHour(exclusionIndex: number){
    return this.getAvailableHoursWithoutInclusion(exclusionIndex)[0]
  }

  generateDefaultPeriod(exclusionIndex: number, timeIndex: number){
    return {
      startHour: parseInt(this.getAvailablePeriodStartHoursWithoutInclusion(exclusionIndex)[0].toString()),
      endHour: parseInt(this.getAvailablePeriodEndHoursWithoutInclusion(exclusionIndex, timeIndex)[0].toString()) 
    }
  }

  checkTimeTypeCanSwitch(exclusionIndex: number, timeIndex: number) {
    return this.exclusions[exclusionIndex].times[timeIndex].type == this.exclusionTimeType.PERIOD ? true : this.checkTimeCanSwitchToPeriod(exclusionIndex)
  }

  checkTimeCanSwitchToPeriod(exclusionIndex: number) {
    return this.getAvailablePeriodStartHoursWithoutInclusion(exclusionIndex).length > 0
  }

  canAddExclusionDay() {
    return this.exclusions.length < this.days.length;
  }

  canAddExclusionTime(exclusionIndex: number) {
    return this.getAvailableHoursWithoutInclusion(exclusionIndex).length > 0;
  }

  startRequest() {
    this.performRequest = true;
  }

  requestComplete() {
    this.performRequest = false;
  }

  getExclusions() {
    this.startRequest();
    this.busyQuery = this.smsExclusionsService.get().pipe(
      tap(_ => {
        this.requestComplete();
      })
    ).subscribe({
      next: (response) => {
        this.idPaarametrage = response.id;
        this.exclusions = response.exclusions;
      }
    })
  }

  onSubmit() {
    this.startRequest();
    this.busyQuery = this.smsExclusionsService.update(this.idPaarametrage, this.exclusions).pipe(
      tap(_ => {
        this.requestComplete();
      })
    ).subscribe({
      next: response => {
        this.getExclusions();
      }
    })
  }
}
