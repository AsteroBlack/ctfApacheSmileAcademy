import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/service/auth.service';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import Swal from 'sweetalert2';
import * as moment from 'moment';
import * as _ from 'lodash';
import { MatTableDataSource } from '@angular/material/table';
import { v4 as uuidv4 } from 'uuid';
import { Logs } from './logs';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-log-action',
  templateUrl: './log-action.component.html',
  styleUrls: ['./log-action.component.scss']
})
export class LogActionComponent implements OnInit {
  items: Array<any> = [];
  itemToSave: any = {};
  user: any = {};
  searchData: any = {
    start: '',
    end: '',
  };
  searchDataLogs: any = {
    start: '',
    end: '',
  };

  itemToSearch: any = {};
  currentPage: number = 0;
  currentPageLogs = 0
  itemsPerPage: number = 10;
  itemsPerPageLogActs: number = 10
  totalItems: number;
  totalItemsLogActs: number
  busyGet: Subscription;
  endPoint: string = 'actionUtilisateur/';
  endPointNew: string = 'actionSimswap/'
  endPointLogActs: string = 'historique/'
  isActiveDate: boolean = false
  isActiveDateLogs: boolean = false
  searchTerm: string
  searchTermName: string;
  itemsTampon: Array<any> = []
  logsInterface !: Logs[];
  itemsTamponLogActs = new MatTableDataSource<any>();
  filePath = ""
  dataActions: Array<any> = []
  dataLogActions = new MatTableDataSource<any>();
  panelOpenState = false;
  dataLogsColumns = [
    'login',
    'nom',
    'prenoms',
    'libelle',
    'dateHeure',
    'adresseIp',
    'machine',
    'statuCo',
    'raison',
    'statuCode',
    'numero',
    'username',
    'nomPrenoms'
  ]
  ConvertDataLogActions: any[];
  ConvertItemsTamponLogActs: any[];
  @ViewChild(MatPaginator) paginator: MatPaginator



  constructor(
    private authService: AuthService,
    private restClient: RestClientService,
    private utilities: UtilitiesService,
  ) {
    console.log("constructor: ", this.dataLogActions);
    this.user = authService.currentUserValue
  }

  ngAfterViewInit(){
    this.dataLogActions.paginator= this.paginator
  }

  pageChanged(event: any): void {
    this.currentPage = event.pageIndex;
    this.itemsPerPage = event.pageSize
    //this.getItems();
    this.getActions()
  }

  pageChangedLogs(event: any, id: any): void {
    this.currentPageLogs = event.pageIndex;
    this.itemsPerPageLogActs = event.pageSize
    //this.getItems();
    this.getLogActions(id, this.searchDataLogs)
  }

  onPeriodeEmitted(event: any) {
    //this.items= []
    this.dataActions = []
    this.searchData.start = moment(event.start).format('DD/MM/YYYY HH:mm:ss');
    this.searchData.end = moment(event.end).format('DD/MM/YYYY HH:mm:ss');
    console.log('start', this.searchData.start);
    console.log('end', this.searchData.end);
    //if(this.isActiveDate)this.getItems(this.searchData)
    if (this.isActiveDate) this.getActions()
  }

  onPeriodeEmittedLogs(event: any, id: any, isActive: boolean) {
    this.dataLogActions = new MatTableDataSource()
    this.searchDataLogs.start = moment(event.start).format('DD/MM/YYYY HH:mm:ss');
    this.searchDataLogs.end = moment(event.end).format('DD/MM/YYYY HH:mm:ss');
    console.log('start', this.searchDataLogs.start);
    console.log('end', this.searchDataLogs.end);
    console.log("activeDate", isActive)
    if (isActive) this.getLogActions(id, this.searchDataLogs)
    console.log("searchDataLogs", this.searchDataLogs)
  }

  test(active: boolean) {
    console.log("test_isActive", active)
  }

  createUniqueId() {
    const id = uuidv4()
    return id
  }

  exportData(param?: any) {
    let data = {
      user: this.user.id,
      isSimpleLoading: false,
      data: {
        id: "",
        createdAt: param?.start ? param.start : moment().format('DD/MM/YYYY HH:mm:ss'),
        createdAtParam: {
          operator: "[]",
          start: param?.start ? param.start : moment().format('DD/MM/YYYY HH:mm:ss'),
          end: param?.end ? param.end : moment().format('DD/MM/YYYY HH:mm:ss')
        },
        isDeleted: "",
        updatedBy: "",
        createdBy: "",
        dateDemande: "",
        idActionEnMasse: "",
        idActionSimswap: 1,
        idStatus: ""
      }
    };

    this.busyGet = this.restClient.post(this.endPoint + 'exportActionUtilisateur', data).subscribe(
      (res) => {
        if (res && res.filePathDoc) {
          res.filePath
          window.location.href = res ? res.filePathDoc : window.location.href;
          /* this.utilities.showNotification(
              'snackbar-success',
              this.utilities.formatMsgServeur(res['status']['message']),
              'bottom',
              'center'
            ); */
        } /* else {
          this.utilities.showNotification(
                'snackbar-danger',
                this.utilities.formatMsgServeur(res['status']['message']),
                'bottom',
                'center'
              );
        } */
      },
      (err) => {
        console.log('Error occured', err);
      }
    );
  }


  exporterFichier(file?: any) {
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



  /*getItems(param?: any) {

    const data = {
      user: this.user.id,
      isSimpleLoading: false,
      data: {
          id: "",
          createdAt: param?.start ? param.start : moment().format('DD/MM/YYYY HH:mm:ss'),
          createdAtParam: {
              operator: "[]",
              start: param?.start ? param.start : moment().format('DD/MM/YYYY HH:mm:ss'),
              end: param?.end ? param.end : moment().format('DD/MM/YYYY HH:mm:ss')
          },
          isDeleted: "",
          updatedBy: "",
          createdBy: "",
          dateDemande: "",
          idActionEnMasse: "",
          idActionSimswap: 1,
          idStatus: ""
      }
    };

    this.busyGet = this.restClient
      .post(this.endPoint + 'exportActionUtilisateur', data)
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
  }*/


  getActions() {

    const req = {
      user: this.user.id,
      data: {

      },
      index: this.currentPage,
      size: this.itemsPerPage
    }

    this.busyGet = this.restClient
      .post(this.endPointNew + 'getByCriteria', req)
      .subscribe(
        {
          next: (res) => {
            if (res && res['items']) {
              this.dataActions = res['items'].map((elt: any) => { return { ...elt, opened: false, isActiveDate: false } });
              this.itemsTampon = this.dataActions
              this.totalItems = res['count'];
            } else {
              this.items = [];
              this.itemsTampon = []
              this.totalItems = 0;

            }
          },
          error: (e) => console.error(e),
          complete: () => console.info('complete')
        }
      );
  }


  getLogActions(id: any, param?: any) {
    const req = {
      user: this.user.id,
      isSimpleLoading: false,
      data: {
        id: "",
        idActionUser: id,
        forLogConnexion: true,
        createdAt: param?.start ? param.start : moment().format('DD/MM/YYYY HH:mm:ss'),
        createdAtParam: {
          operator: "[]",
          start: param?.start ? param.start : moment().format('DD/MM/YYYY HH:mm:ss'),
          end: param?.end ? param.end : moment().format('DD/MM/YYYY HH:mm:ss')
        }
      },
      index: this.currentPageLogs,
      size: this.itemsPerPageLogActs
    }

    this.busyGet = this.restClient
      .post(this.endPointLogActs + 'exportLogsUser', req)
      .subscribe(
        {
          next: (res) => {
            if (res && res['items']) {
              this.dataLogActions = res['items'].map((elt: any) => { return { ...elt, filePath: res['filePathDoc'] ? res['filePathDoc'] : "" } });
              this.itemsTamponLogActs = this.dataLogActions
              this.filePath = res['filePathDoc'] ? res['filePathDoc'] : ""
              this.totalItemsLogActs = res['count'];
              console.log("dataLogs", this.dataLogActions)
            } else {
              this.dataLogActions = new MatTableDataSource();
              this.itemsTamponLogActs = new MatTableDataSource()
              this.totalItemsLogActs = 0;
              this.filePath = ""
            }
          },
          error: (e) => console.error(e),
          complete: () => console.info('complete')
        }
      );
  }

  //mettre en place un filter de matDataTable

  filtre(value:string){
    
    this.dataLogActions.filter= value.trim().toLowerCase()
    if(this.dataLogActions.paginator){
      this.dataLogActions.paginator.firstPage()
    }

    //this.totalItemsLogActs= this.dataLogActions.data.length

    console.log("filter dataLogActions",this.dataLogActions);
    //console.log("filter dataLogActions.data",this.dataLogActions.data);
    console.log("filter dataLogActions.filter",this.dataLogActions.filter);
    console.log("paginator", this.dataLogActions.paginator)
  }


  /*filterItems() {

    console.log("Initial dataLogActions:", this.dataLogActions);
    console.log("Initial dataLogActions.data:", this.dataLogActions.data);
    console.log("Initial itemsTamponLogActs:", this.itemsTamponLogActs);


    this.ConvertDataLogActions = Object.values(this.dataLogActions);
    this.ConvertItemsTamponLogActs = Object.values(this.itemsTamponLogActs);

    console.log("ConvertMatTableDataSource", this.ConvertDataLogActions);
    console.log("ConvertItemsTamponLogActs", this.ConvertItemsTamponLogActs);
    console.log("this.searchTerm", this.searchTerm);

    if (!this.searchTerm || this.searchTerm == "") {
      this.ConvertDataLogActions = this.ConvertItemsTamponLogActs;
      console.log("No searchTerm :", this.ConvertDataLogActions);
      return;
    }

    const searchTermLower = this.searchTerm.toLowerCase()


    this.ConvertDataLogActions = this.ConvertDataLogActions.filter(item => {
      const userLogin = (item.login || "").toLowerCase()
      const userNom = (item.userNom || "").toLowerCase()
      const userPrenom = (item.userPrenom || "").toLowerCase()
      const actionSimswapLibelle = item.actionSimswapLibelle || ""
      const createdAt = item.createdAt || ""
      const ipAdress = item.ipadress || ""
      const machine = item.machine || ""
      const statusConnection = (item.statusConnection || "").toLowerCase()

      let isMatch = false;

      isMatch = (
        userLogin.includes(searchTermLower) ||
        userNom.includes(searchTermLower) ||
        userPrenom.includes(searchTermLower) ||
        actionSimswapLibelle.includes(searchTermLower) ||
        createdAt.includes(searchTermLower) ||
        ipAdress.includes(searchTermLower) ||
        machine.includes(searchTermLower) ||
        statusConnection.includes(searchTermLower)
      );

      console.log("match", isMatch)
      return isMatch

    }

    )
    console.log("Filtered data:", this.ConvertDataLogActions);

    const array = this.dataLogActions.data.map(item => {
      console.log("array: ", array);
      return {
        login: item.login,
        userNom: item.userNom,
        userPrenom: item.userPrenom,
        actionSimswapLibelle: item.actionSimswapLibelle,
        createdAt: item.createdAt,
        ipadress: item.ipadress,
        machine: item.machine,
        statusConnection: item.statusConnection
      };

    });
    /*
    console.log("tab filtered", this.dataLogActions)
    this.dataLogActions.filterPredicate = function(data: any, filter: string){
      console.log("data", data.userLogin);
      console.log("data", data)
      return data.userLogin.toLowerCase().includes(filter);
    } 
    console.log("filterPredicate: ", this.dataLogActions.filterPredicate);


    this.dataLogActions.filter= value.trim().toLowerCase()
    console.log("datalogActiols.filter: ",this.dataLogActions.filter);
    console.log("tab filtered", this.dataLogActions)
    console.log("value", value)
    
  }
  */

  

  filterItemsName() {

    if (!this.searchTermName || this.searchTermName == "") {
      this.dataActions = this.itemsTampon
      return;
    }

    const searchTermLower = this.searchTermName.toLowerCase()

    this.dataActions = this.dataActions.filter(item => {
      const libelle = (item.libelle || "").toLowerCase()
      return (
        libelle.includes(searchTermLower)
      )
    })

    console.log("tab filtered", this.dataActions)
  }


  ngOnInit(): void {
    //this.getItems()
    this.getActions()

    
  }

}
