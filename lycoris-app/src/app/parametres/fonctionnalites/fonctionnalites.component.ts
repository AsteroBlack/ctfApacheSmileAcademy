import { Component, OnInit, TemplateRef } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/service/auth.service';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import Swal from 'sweetalert2';
import * as moment from 'moment';

@Component({
  selector: 'app-fonctionnalites',
  templateUrl: './fonctionnalites.component.html',
  styleUrls: ['./fonctionnalites.component.scss'],
})
export class FonctionnalitesComponent implements OnInit {
  items: Array<any> = [];
  itemToSave: any = {};
  modalRef: BsModalRef;
  user: any = {};
  itemToSearch: any = {};
  currentPage: number = 1;
  itemsPerPage: number = 10;
  totalItems: number;
  busyGet: Subscription;
  busySave: Subscription;
  loading: boolean = false;
  endPoint: string = 'fonctionnalite/';
  bsValue: Date;
  ListNoParentFonctionnalites: any[];

  constructor(
    private authService: AuthService,
    private restClient: RestClientService,
    private modalService: BsModalService,
    private utilities: UtilitiesService
  ) {
    this.user = this.authService.currentUserValue;
  }

  pageChanged(event: any): void {
    this.currentPage = event.page;
    this.getItems();
  }
  resetForm() {
    this.itemToSave.libelle = '';
  }

  confirmSaveItem(item) {
    let objToSave = Object.assign({}, item);
    if (!item || !item.libelle) {
      this.utilities.showNotification(
        'snackbar-danger',
        'Veuillez renseigner libelle !',
        'bottom',
        'center'
      );
      return;
    }
    Swal.fire({
      title: objToSave?.id ? 'Modification' : 'Création',
      text: objToSave?.id
        ? 'Vous êtes sur le point de modifier un élément. Voulez-vous poursuivre cette action ?'
        : "Vous êtes sur le point d'enregistrer un élément. Voulez-vous poursuivre cette action ?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non',
    }).then((result) => {
      if (result.value) {
        // objToSave.libelle = objToSave.libelle.toUpperCase();
        this.saveItem(objToSave);
      }
    });
  }

  openModal(data: any, template: TemplateRef<any>) {
    let config = {
      backdrop: true,
      ignoreBackdropClick: true,
      id: 1,
      class: 'modal-md',
    };
    this.itemToSave = {};
    if (data) {
      console.log('data', data);
      // Lorsque nous sommes en modification
      this.itemToSave = Object.assign({}, data);
      if (data.parentId) {
        this.itemToSave.idParent = data.idParent;
      }
    }

    this.modalRef = this.modalService.show(template, config);
  }

  saveItem(item) {
    this.loading = true;
    let itemAEnregistrer = Object.assign({}, item, {
      class: 'full-screen-modal modal-lg',
    });
    var request = {
      user: this.user.id,
      datas: [itemAEnregistrer],
    };

    this.busySave = this.restClient
      .post(
        this.endPoint + '' + (itemAEnregistrer.id ? 'update' : 'create'),
        request
      )
      .subscribe(
        (res) => {
          console.log('resul', res);
          this.loading = false;
          if (!res['hasError']) {
            if (res['items'] && res['items'].length > 0) {
              this.utilities.showNotification(
                'snackbar-success',
                this.utilities.formatMsgServeur(res['status']['message']),
                'bottom',
                'center'
              );

              this.getItems();
              this.modalRef.hide();
              this.getParentFunctionnalites();
            }
          } else {
            if (res['status'] && res['status']['message']) {
              this.utilities.showNotification(
                'snackbar-danger',
                this.utilities.formatMsgServeur(res['status']['message']),
                'bottom',
                'center'
              );
            }
          }
        },
        (err) => {
          this.utilities.showNotification(
            'snackbar-danger',
            this.utilities.getMessageEndPointNotAvailble(),
            'bottom',
            'center'
          );
          this.loading = false;
        }
      );
  }

  getParentFunctionnalites() {
    let request = {
      user: this.user.id,
      data: {},
    };

    this.busyGet = this.restClient
      .post(this.endPoint + 'getByCriteria', request)
      .subscribe(
        (res) => {
          if (res && res['items']) {
            this.ListNoParentFonctionnalites = res['items'].filter(
              (r) => !r.idParent
            );
            // this.ListNoParentFonctionnalites.map(lnpf=>lnpf.parentId = lnpf.id)
          } else {
            this.ListNoParentFonctionnalites = [];
          }
        },
        (err) => {}
      );
  }

  getItems() {
    let request = {
      user: this.user.id,
      data: {
        libelle: this.itemToSearch.libelle ? this.itemToSearch.libelle : null,
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
            this.totalItems = res['count'];
          } else {
            this.items = [];
            this.totalItems = 0;
          }
        },
        (err) => {}
      );
  }

  confirmDelete(item) {
    Swal.fire({
      title: 'Suppression',
      text: 'Vous êtes sur le point de supprimer une fontionnalité. Voulez-vous poursuivre cette action ?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non',
    }).then((result) => {
      if (result.value) {
        this.deleteItem(item);
      }
    });
  }

  deleteItem(obj) {
    var request = {
      user: this.user.id,
      datas: [obj],
    };

    this.busyGet = this.restClient
      .post(this.endPoint + 'delete', request)
      .subscribe(
        (res) => {
          console.log(res);
          if (!res['hasError']) {
            this.utilities.showNotification(
              'snackbar-success',
              this.utilities.formatMsgServeur(res['status']['message']),
              'bottom',
              'center'
            );

            this.currentPage = 1;
            this.getItems();
          } else {
            if (res['status'] && res['status']['message']) {
              this.utilities.showNotification(
                'snackbar-danger',
                this.utilities.formatMsgServeur(res['status']['message']),
                'bottom',
                'center'
              );
            }
          }
        },
        (err) => {
          console.log('Error occured', err);
          this.utilities.showNotification(
            'snackbar-danger',
            this.utilities.getMessageEndPointNotAvailble(),
            'bottom',
            'center'
          );
        }
      );
  }
  ngOnInit() {
    this.resetForm();
    this.getItems();
    this.getParentFunctionnalites();
  }
}
