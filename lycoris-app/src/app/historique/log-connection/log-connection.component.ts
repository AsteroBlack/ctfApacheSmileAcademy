import { Component, OnInit, TemplateRef } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/service/auth.service';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import { tap, switchMap } from 'rxjs/operators';
import { catchError, map, throwError } from "rxjs";
import Swal from 'sweetalert2';
import * as moment from 'moment';
import * as _ from 'lodash';

@Component({
  selector: 'app-log-connection',
  templateUrl: './log-connection.component.html',
  styleUrls: ['./log-connection.component.scss']
})
export class LogConnectionComponent implements OnInit {

  isActiveDate = false;

  items: Array<any> = [];
  itemToSave: any = {};
  user: any = {};

  itemToSearch: any = {};
  currentPage: number = 0;
  itemsPerPage: number = 50;
  totalItems: number;
  busyGet: Subscription;
  endPoint: string = 'historique/';
  searchData: any = {}; // Options de recherche pour le filtrage des dates
  filePath= ""
  isLogUser: boolean= false
  searchTerm: string
  itemsTampon: Array<any>= []


  constructor(
    private authService: AuthService,
    private restClient: RestClientService,
    private utilities: UtilitiesService,
  ) {
    this.user = this.authService.currentUserValue;
  }



  pageChanged(event: any): void {
    this.currentPage = event.page;
    this.getItems();
  }

  exporterFichier(){
    if (this.filePath) {
      window.open(this.filePath); // Ouvre le lien de téléchargement dans une nouvelle fenêtre
      this.utilities.showNotification(
        'snackbar-success',
        this.utilities.formatMsgServeur('Téléchargement en cours...'),
        'bottom',
        'center'
      );
    } else {
      this.utilities.showNotification(
        'snackbar-danger',
        this.utilities.formatMsgServeur('échec du téléchargement'),
        'bottom',
        'center'
      );
    }
}


  PeriodEmitted(event: any) {
    this.filePath= ""
    console.log('event', event);
    this.searchData.start = moment(event.start).format('DD/MM/YYYY HH:mm:ss');
    this.searchData.end = moment(event.end).format('DD/MM/YYYY HH:mm:ss');
    console.log('start', this.searchData.start);
    console.log('end', this.searchData.end);
    if (this.isActiveDate ) {
      this.getItems(this.searchData);
    }
  }



  getItems(param? :any) {
    let request = {
      user: this.user.id,
      isSimpleLoading: false,
      data: {
        id: "",
        idActionUser: 17,
        forLogConnexion: this.isLogUser,
        createdAt:   param?.start ? param.start : "",
        createdAtParam: {
          operator: '[]',
        start: param?.start ? param.start : "",
        end: param?.end ? param.end : "",
        }
      }
    };

    this.busyGet = this.restClient
      .post(this.endPoint + 'exportLogsUser', request)
      .subscribe(
        {
          next: (res) => {
            if (res && res['items']) {
              this.items= res['items']
              this.itemsTampon= res['items']
              this.totalItems = res['count'];
              this.filePath= res['filePathDoc'] ? res['filePathDoc'] : ""
              console.log(this.items)
            } else {
              this.items = [];
              this.itemsTampon= []
              this.totalItems = 0;
            }
          },
          error: (e) => console.error(e),
          complete: () => console.info('complete')
        }
      );
  }


  filterItemsAllCo(){
    if(!this.searchTerm || this.searchTerm == ""){
      this.items= this.itemsTampon
      return;
    }

    const searchTermLower= this.searchTerm.toLowerCase()

    this.items= this.items.filter( item => {
      const userLogin= (item.login || "").toLowerCase()
      const userNom= (item.nom || "").toLowerCase()
      const userPrenom= (item.prenom || "").toLowerCase()
      const action= (item.actionService || "").toLowerCase()
      const machine= (item.machine || "").toLowerCase()
      const statuCo= (item.statusConnection || "").toLowerCase()
      const ipAdress= (item.ipadress || "").toLowerCase()
      
      return (
        userLogin.includes(searchTermLower) ||
        userNom.includes(searchTermLower) ||
        userPrenom.includes(searchTermLower) ||
        action.includes(searchTermLower) ||
        machine.includes(searchTermLower) ||
        statuCo.includes(searchTermLower) ||
        ipAdress.includes(searchTermLower)
      )
    })

    console.log("tab filtered", this.items)
  }


  filterItemsFirstCo(){
    if(!this.searchTerm || this.searchTerm == ""){
      this.items= this.itemsTampon
      return;
    }

    const searchTermLower= this.searchTerm.toLowerCase()
    
    this.items= this.items.filter( item => {
      const userLogin= (item.login || "").toLowerCase()
      const firstCo= (item.firstConnection || "").toLowerCase()
      const lastCo= (item.lastConnection || "").toLowerCase()
      const machine= (item.machine || "").toLowerCase()
      const ipAdress= (item.ipadress || "").toLowerCase()
      const statusCo= (item.statusConnection || "").toLowerCase()
      
      return (
        userLogin.includes(searchTermLower) ||
        firstCo.includes(searchTermLower) ||
        lastCo.includes(searchTermLower) ||
        machine.includes(searchTermLower) ||
        ipAdress.includes(searchTermLower) ||
        ipAdress.includes(searchTermLower) ||
        statusCo.includes(searchTermLower)
      )
    })

    console.log("tab filtered", this.items)
  }


        // user: this.user.id,
      // data: {
      //   login: this.itemToSearch.login ? this.itemToSearch.login : null,
      // },
      // index: this.currentPage - 1,
      // size: this.itemsPerPage,


    // this.busyGet = this.restClient
    //   .post(this.endPoint + 'exportLogsUser', request)
    //   .subscribe(
    //     {
    //       next: (res) => {
    //         if (res && res['items']) {
    //         this.items = res['items'];
    //         this.totalItems = res['count'];
    //       } else {
    //         this.items = [];
    //         this.totalItems = 0;
    //       }},
    //       error: (e) => console.error(e),
    //       complete: () => console.info('complete')
    //   }
    //   );

  ngOnInit(): void {
    this.getItems();
  }
}





