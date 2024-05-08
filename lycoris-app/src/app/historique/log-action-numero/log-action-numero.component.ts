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
  selector: 'app-log-action-numero',
  templateUrl: './log-action-numero.component.html',
  styleUrls: ['./log-action-numero.component.scss']
})
export class LogActionNumeroComponent implements OnInit {
  items: Array<any> = [];
  itemToSave: any = {};
  user: any = {};

  itemToSearch: any = {};
  currentPage: number = 1;
  itemsPerPage: number = 50;
  totalItems: number;
  busyGet: Subscription;
  endPoint: string = 'numeroStories/';
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

  exportData() {
    let data = {
      user: this.user.id,
      data: {},
    };

    this.busyGet = this.restClient.post('numeroStories/exportNumeroStorieFile', data).subscribe(
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

  getItems() {
    let request = {
      user: this.user.id,
      data: {
        numero: this.itemToSearch.numero ? this.itemToSearch.numero : null,
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

  ngOnInit() {
    this.getItems();
  }
}
