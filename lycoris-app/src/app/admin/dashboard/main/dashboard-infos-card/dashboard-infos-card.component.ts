import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard-infos-card',
  templateUrl: './dashboard-infos-card.component.html',
  styleUrls: ['./dashboard-infos-card.component.scss']
})
export class DashboardInfosCardComponent implements OnInit {
  @Input() label: string;
  @Input() label2: string;
  @Input() icon: string;
  @Input() totalValue: number;
  @Input() leftValueLabel: string;
  @Input() leftValue: number;
  @Input() rightValueLabel: string;
  @Input() rightValue: number;
  @Input() userProperties: {code: string, taille: number}
  

  public cardChart1: any;
  public cardChart1Data: any;
  public cardChart1Label: any;

  constructor() { }

  ngOnInit(): void {
    this.smallChart1();
  }
  private smallChart1() {
    this.cardChart1 = {
      responsive: true,
      tooltips: {
        enabled: false,
      },
      legend: {
        display: false,
      },
      scales: {
        yAxes: [
          {
            gridLines: {
              display: false,
              drawBorder: false,
            },
            ticks: {
              beginAtZero: true,
              display: false,
            },
          },
        ],
        xAxes: [
          {
            gridLines: {
              drawBorder: false,
              display: false,
            },
            ticks: {
              display: false,
            },
          },
        ],
      },
      title: {
        display: false,
      },
    };
    this.cardChart1Data = [
      {
        label: 'New Patients',
        data: [50, 61, 80, 50, 72, 52, 60, 41, 30, 45, 70, 40, 93, 63, 50, 62],
        borderWidth: 4,
        pointStyle: 'circle',
        pointRadius: 4,
        borderColor: 'rgba(103,119,239,.7)',
        pointBackgroundColor: 'rgba(103,119,239,.2)',
        backgroundColor: 'rgba(103,119,239,.2)',
        pointBorderColor: 'transparent',
      },
    ];
    this.cardChart1Label = [
      '16-07-2018',
      '17-07-2018',
      '18-07-2018',
      '19-07-2018',
      '20-07-2018',
      '21-07-2018',
      '22-07-2018',
      '23-07-2018',
      '24-07-2018',
      '25-07-2018',
      '26-07-2018',
      '27-07-2018',
      '28-07-2018',
      '29-07-2018',
      '30-07-2018',
      '31-07-2018',
    ];
  }

}
