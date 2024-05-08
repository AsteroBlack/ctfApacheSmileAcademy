import { Directive, Output, EventEmitter, Input, OnInit, ElementRef, AfterViewInit } from '@angular/core';
import * as $ from 'jquery';
import * as moment from 'moment';
import 'daterangepicker';

const frenchLocal: any = {
  format: 'DD MMMM YYYY',
  separator: ' au ',
  applyLabel: 'Appliquer',
  cancelLabel: 'Annuler',
  fromLabel: 'De',
  toLabel: 'A',
  customRangeLabel: 'Modifier',
  weekLabel: 'S',
  daysOfWeek: [
      'Di',
      'Lu',
      'Ma',
      'Me',
      'Je',
      'Ve',
      'Sa'
  ],
  monthNames: [
      'Janvier',
      'Fevrier',
      'Mars',
      'Avril',
      'Mai',
      'Juin',
      'Juillet',
      'Août',
      'Septembre',
      'Octobre',
      'Novembre',
      'Decembre'
  ],
  firstDay: 1
};
const frenchRange: any = {
  'Aujourd hui': [moment().startOf('day'), moment().endOf('day')],
  'Hier': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
  '7 derniers jours': [moment().subtract(6, 'days').startOf('day'), moment()],
  '30 derniers jours': [moment().subtract(29, 'days'), moment()],
  'Ce mois': [moment().startOf('month'), moment()],
  'Le mois dernier': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
  'Tout': [moment.unix(999), moment().clone()]
};

export interface Period{ start: any, end: any }

@Directive({
  selector: '[appDateRangeSelector]'
})
export class DateRangeSelectorDirective implements OnInit, AfterViewInit {

  @Output() periode = new EventEmitter<Period>();
  @Input() alwaysShowCalendars = true;
  @Input() defaultRange: string;
  rangesData: any = {};
  today = 'today';
  lang = 'fr';
  start: any = moment().startOf('day'); /* start: any = moment().subtract(30, 'days').startOf('day'); */
  end: any = moment().endOf('day');
  calendarId: string;

  constructor(private el: ElementRef) { }

  ngOnInit() {
    console.log('lollllg');
    if (this.defaultRange) {
      console.log('lollllg');
    }
  }

  ngAfterViewInit(): void {
    this.calendarId = '#' + this.el.nativeElement.id;
    moment.locale('fr');
    this.periode.emit({ start: this.start.valueOf(), end: this.end.valueOf() });
    console.log('mutation', this.start.format('DD/MM/YYYY HH:mm:ss'), this.end.format('DD/MM/YYYY HH:mm:ss'));
    const element: any = $(this.calendarId);
    element.daterangepicker({
      startDate: this.start.locale('fr'),
      endDate: this.end.locale('fr'),
      opens: 'left',
      timePicker: true,
      maxDate: moment(),
      showDropdowns: true,
      alwaysShowCalendars: this.alwaysShowCalendars,
      ranges: frenchRange,
      locale: frenchLocal
    });
    this.analyzeDate({ startDate: this.start, endDate: this.end });

    $(this.calendarId).on('apply.daterangepicker', (ev: any, picker: any) => {
      this.start = picker.startDate;
      this.end = picker.endDate;
      console.log('apply.daterangepicker', picker.endDate.valueOf() - picker.startDate.valueOf());
      this.periode.emit({ start: picker.startDate.valueOf(), end: picker.endDate.valueOf() });
      this.analyzeDate({ startDate: this.start, endDate: this.end });
    });

  }

  analyzeDate(pick: any): string {
    let str = '';
    console.log('7 derniers', pick.endDate.valueOf(), pick.startDate.valueOf());
    if (pick.endDate.valueOf() === moment().valueOf() && pick.startDate.valueOf() === moment().subtract(1, 'days').valueOf()) {
      // Last 24 hours
      str = "Les dernières 24H";
    } else if (pick.endDate.valueOf() === moment().endOf('day').valueOf() && pick.startDate.valueOf() === moment().startOf('day').subtract(6, 'days').valueOf()) {
      // Last 7 days
      str = "Les 7 derniers jours";
    } else if (pick.endDate.valueOf() === moment().endOf('day').valueOf() && pick.startDate.valueOf() === moment().startOf('day').subtract(29, 'days').valueOf()) {
      // Last 30 days
      str = "Les 30 derniers jours";
    } else if (pick.endDate.valueOf() === moment().endOf('day').valueOf() && pick.startDate.valueOf() === moment().startOf('month').startOf('day').valueOf()) {
      // This month
      str = "Ce mois";
    } else if (pick.endDate.valueOf() === moment().endOf('day').valueOf() && pick.startDate.valueOf() === moment().startOf('day').valueOf()) {
      // Today
      str = "Aujourd'hui";
    } else if (pick.endDate.valueOf() === moment().endOf('day').subtract(1, 'days').valueOf() && pick.startDate.valueOf() === moment().startOf('day').subtract(1, 'days').valueOf()) {
      // Yesterday
      str = "Hier";
    } else if (pick.endDate.valueOf() === moment().subtract(1, 'month').endOf('month').valueOf() && pick.startDate.valueOf() === moment().subtract(1, 'month').startOf('month').valueOf()) {
      // Last month
      str = "Le mois dernier";
    } else if (pick.startDate.valueOf() === moment.unix(999).valueOf()) {
      // All
      str = "Tout";
    } else {
      str = '';
    }
    str += ' ';
    const unionWord = this.lang === 'fr' ? 'au' : 'to';
    if (str !== ' ') {
      $(this.calendarId + ' span').html(str);
    } else {
      $(this.calendarId + ' span')
        .html(this.start.locale('fr').format('DD MMMM YYYY ') + unionWord + this.end.locale('fr').format(' DD MMMM YYYY') + '&nbsp;');
    }
    return str + ' ';
  }

}
