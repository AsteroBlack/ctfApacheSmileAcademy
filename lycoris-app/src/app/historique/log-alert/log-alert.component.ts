import { Component, OnInit, TemplateRef } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/service/auth.service';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import Swal from 'sweetalert2';
import * as moment from 'moment';
import * as _ from 'lodash';

@Component({
  selector: 'app-log-alert',
  templateUrl: './log-alert.component.html',
  styleUrls: ['./log-alert.component.scss']
})
export class LogAlertComponent implements OnInit {

  items: Array<any> = [];
  itemToSave: any = {};
  user: any = {};
  selectedMessage: string = "";
  modalRef: BsModalRef;

  itemToSearch: any = {};
  currentPage: number = 1;
  itemsPerPage: number = 50;
  totalItems: number;
  busyGet: Subscription;
  endPoint: string = 'ationToNotifiable/';
  searchTerm: string
  itemsTampon: Array<any>= []
  filePath= ""
  isActiveDate:boolean= false
  searchData: any = {
    start: '',
    end: '',
  };


  constructor(
    private authService: AuthService,
    private restClient: RestClientService,
    private utilities: UtilitiesService,
    private modalService: BsModalService
  ) {
    this.user = this.authService.currentUserValue;
  }

  pageChanged(event: any): void {
    this.currentPage = event.page;
    if(this.isActiveDate){
      this.getItems(this.searchData)
    }else this.getItems();
  }

  onPeriodeEmitted(event) {
    this.filePath= ""
    console.log('event', event);
    this.searchData.start = moment(event.start).format('DD/MM/YYYY HH:mm:ss');
    this.searchData.end = moment(event.end).format('DD/MM/YYYY HH:mm:ss');
    console.log('start', this.searchData.start);
    console.log('end', this.searchData.end);
    if(this.isActiveDate)this.getItems(this.searchData);
  }

  exportData() {
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

  getItems(param?: any) {
    let request = {
      user: this.user.id,
      data: {
        id: "",
        idStatus: "",
        idUserDemand: "",
        idUserValid: "",
        idAction: "",
        numero: "",
        message: "",
        isNotify: "",
        isEnMasse: "",
        statutAction: "",
        createdAt: param?.start ? param.start : moment().format('DD/MM/YYYY HH:mm:ss'),
        createdAtParam: {
          operator: '[]',
          start: param?.start ? param.start : moment().format('DD/MM/YYYY HH:mm:ss'),
          end: param?.end ? param.end : moment().format('DD/MM/YYYY HH:mm:ss'),
        },
        createdBy: "",
        updatedAt: "",
        updatedBy: "",
        isDeleted: ""
      },
      index: this.currentPage - 1,
      size: this.itemsPerPage,
    };


    this.busyGet = this.restClient
      .post(this.endPoint + 'getByCriteria', request)
      .subscribe(
        {
          next: (res) => {
            if (res && res['items']) {
            this.items = res['items'];
            this.itemsTampon= res['items']
            this.totalItems = res['count'];
            this.filePath= res['filePathDoc'] ? res['filePathDoc'] : ""
          } else {
            this.items = [];
            this.itemsTampon= []
            this.totalItems = 0;
          }},
          error: (e) => console.error(e),
          complete: () => console.info('complete') 
      }
      );
  }

  openMessagePopup(message: string): void {
    this.selectedMessage = message;
    console.log(this.selectedMessage)
  }

  filterItems(){

    if(!this.searchTerm || this.searchTerm == ""){
      this.items= this.itemsTampon
      return;
    }

    const searchTermLower= this.searchTerm.toLowerCase()

    this.items= this.items.filter( item => {
      const userLogin= (item.userLogin || "").toLowerCase()
      const numero= item.numero || ""
      const userNom= (item.userNom || "").toLowerCase()
      const userPrenom= (item.userPrenom || "").toLowerCase()
      const statusCode= (item.statusCode || "").toLowerCase()
      const statut= (item.statut || "").toLowerCase()
      const profilLibelle= (item.profilLibelle || "").toLowerCase()

      return (
        userLogin.includes(searchTermLower) ||
        numero.includes(searchTermLower) ||
        userNom.includes(searchTermLower) ||
        userPrenom.includes(searchTermLower) ||
        statut.includes(searchTermLower) ||
        profilLibelle.includes(searchTermLower) ||
        statusCode.includes(searchTermLower)
      )
    })

    console.log("tab filtered", this.items)
  }

  openModal(data: any, template: TemplateRef<any>) {
    let config = {
      backdrop: true,
      ignoreBackdropClick: true,
      id: 1,
      class: 'modal-lg modal-width-50',
    };
    this.itemToSave = {};
    if (data) {
      // Lorsque nous sommes en modification
      console.log('data', data);
      this.itemToSave = Object.assign({}, data);
    }
    this.modalRef = this.modalService.show(template, config);
  }

  ngOnInit() {
    this.getItems();
  }

}
