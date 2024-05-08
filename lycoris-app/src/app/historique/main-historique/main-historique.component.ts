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
  selector: 'app-main-historique',
  templateUrl: './main-historique.component.html',
  styleUrls: ['./main-historique.component.scss'],
})
export class MainHistoriqueComponent implements OnInit {
  items: Array<any> = [];
  itemToSave: any = {};
  user: any = {};

  itemToSearch: any = {};
  currentPage: number = 1;
  itemsPerPage: number = 50;
  totalItems: number;
  busyGet: Subscription;
  endPoint: string = 'historique/';
  searchTerm: string
  itemsTampon: Array<any>= []

  constructor(
    private authService: AuthService,
    private restClient: RestClientService,
    //private utilities: UtilitiesService,
  ) {
    this.user = this.authService.currentUserValue;
  }

  pageChanged(event: any): void {
    this.currentPage = event.page;
    this.getItems();
  }
  resetForm() {
    this.itemToSave.login = '';
  }

  exportData() {
    let data = {
      user: this.user.id,
      data: {},
    };

    this.busyGet =  this.restClient.post('historique/exportHistoriqueFile', data).subscribe(
      (res) => {
        if (res && res.filePathDoc) {
         res.filePath
          window.location.href = res ? res.filePathDoc : window.location.href;
          /*this.utilities.showNotification(
              'snackbar-success',
              this.utilities.formatMsgServeur(res['status']['message']),
              'bottom',
              'center'
            ); */
        } /*else {
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

  getItems() {
    let request = {
      user: this.user.id,
      data: {
        login: this.itemToSearch.login ? this.itemToSearch.login : null,
      },
      index: this.currentPage - 1,
      size: this.itemsPerPage,
    };

    this.busyGet = this.restClient
      .post(this.endPoint + 'getByCriteria', request)
      .subscribe(
        (res) => {
          if (res && res['items']) {
            this.items = res['items'];
            this.itemsTampon= res['items']
            this.totalItems = res['count'];
          } else {
            this.items = [];
            this.itemsTampon= []
            this.totalItems = 0;
          }
        },
        (err) => {}
      );
  }

  filterItems(){

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
      
      return (
        userLogin.includes(searchTermLower) ||
        userNom.includes(searchTermLower) ||
        userPrenom.includes(searchTermLower) ||
        action.includes(searchTermLower)
      )
    })

    console.log("tab filtered", this.items)
  }

  ngOnInit() {
    this.resetForm();
    this.getItems();
  }
}
