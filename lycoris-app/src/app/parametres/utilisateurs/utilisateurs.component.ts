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
  selector: 'app-utilisateurs',
  templateUrl: './utilisateurs.component.html',
  styleUrls: ['./utilisateurs.component.scss'],
})
export class UtilisateursComponent implements OnInit {
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
  endPoint: string = 'user/';
  itemsProfiles: any;
  itemsCivilite: any;
  itemsCategory: any;
  bsValue: Date;
  filePath= ""
  searchTerm: string
  itemsTampon: Array<any>= []

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
    this.itemToSave.login = '';
  }
  confirmSaveItem(item) {
    if (!item || !item.idCivilite) {
      this.utilities.showNotification(
        'snackbar-danger',
        'Veuillez renseigner civilité !',
        'bottom',
        'center'
      );
      return;
    }
    if (!item || !item.nom) {
      this.utilities.showNotification(
        'snackbar-danger',
        'Veuillez renseigner nom !',
        'bottom',
        'center'
      );
      return;
    }

    if (!item || !item.prenom) {
      this.utilities.showNotification(
        'snackbar-danger',
        'Veuillez renseigner prenom !',
        'bottom',
        'center'
      );
      return;
    }

    if (!item || !item.idProfil) {
      this.utilities.showNotification(
        'snackbar-danger',
        'Veuillez renseigner profil !',
        'bottom',
        'center'
      );
      return;
    }

    if (!item || !item.login) {
      this.utilities.showNotification(
        'snackbar-danger',
        'Veuillez renseigner login !',
        'bottom',
        'center'
      );
      return;
    }

    if (!item || !item.idCategory) {
      this.utilities.showNotification(
        'snackbar-danger',
        'Veuillez renseigner Categorie !',
        'bottom',
        'center'
      );
      return;
    }

    let objToSave = Object.assign({}, item);
    Swal.fire({
      title: 'Utilisateur',
      text: objToSave?.id
        ? 'Vous êtes sur le point de modifier un utilisateur. Voulez-vous poursuivre cette action ?'
        : "Vous êtes sur le point d'enregistrer un utilisateur. Voulez-vous poursuivre cette action ?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non',
    }).then((result) => {
      if (result.value) {
        this.saveItem(objToSave);
      }
    });
  }

  openModal(data: any, template: TemplateRef<any>) {
    let config = {
      backdrop: true,
      ignoreBackdropClick: true,
      id: 1,
      class: 'modal-lg',
    };
    this.itemToSave = {};
    if (data) {
      // Lorsque nous sommes en modification
      console.log('data', data);
      this.itemToSave = Object.assign({}, data);
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
      .post(this.endPoint + '/getByCriteria', request)
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
            }
          },
          error: (err) => console.info(err),
          complete: () => console.info('complete'),
        }
      );
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

  getItemsProfile() {
    let request = {
      user: this.user.id,
      data: {},
    };

    this.busyGet = this.restClient
      .post('profil/getByCriteria', request)
      .subscribe(
        (res) => {
          console.log('itemsProfiles', res);
          if (res && res['items']) {
            this.itemsProfiles = res['items'];
          } else {
            this.itemsProfiles = [];
          }
        },
        (err) => {}
      );
  }

  getItemsCategory() {
    let request = {
      user: this.user.id,
      data: {},
    };

    this.busyGet = this.restClient
      .post('category/getByCriteria', request)
      .subscribe(
        (res) => {
          console.log('itemsCategory', res);
          if (res && res['items']) {
            this.itemsCategory = res['items'];
          } else {
            this.itemsCategory = [];
          }
        },
        (err) => {}
      );
  }

  getItemsCivilite() {
    let request = {
      user: this.user.id,
      data: {},
    };

    this.busyGet = this.restClient
      .post('civilite/getByCriteria', request)
      .subscribe(
        (res) => {
          if (res && res['items']) {
            this.itemsCivilite = res['items'];
          } else {
            this.itemsCivilite = [];
          }
        },
        (err) => {}
      );
  }

  confirmDelete(item) {
    Swal.fire({
      title: 'Utilisateur',
      text: 'Vous êtes sur le point de supprimer cet utilisateur. Voulez-vous poursuivre cette action ?',
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
    let request = {
      user: this.user.id,
      datas: [{id:obj}],
    };

    this.busyGet = this.restClient
      .post(this.endPoint + 'delete', request)
      .subscribe(
        {
          next: (res) => {
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
          error: (err) => console.info(err),
          complete: () => console.info('complete'),
        }
      );
  }

  lockUser(item) {
    Swal.fire({
      title: 'Bloquer l\'utilisateur',
      text: 'Vous êtes sur le point de bloquer l\'utilisateur. Voulez-vous poursuivre cette action ?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non',
    }).then((result) => {
      if (result.value) {
        this.lock(item);
      }
    });
  }

  lock(obj) {
    var request = {
      user: this.user.id,
      datas: [{id:obj}],
    };

    this.busyGet = this.restClient
      .post(this.endPoint + 'blockUser', request)
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

  unlockUser(item) {
    Swal.fire({
      title: 'Débloquer l\'utilisateur',
      text: 'Vous êtes sur le point de débloquer l\'utilisateur. Voulez-vous poursuivre cette action ?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non',
    }).then((result) => {
      if (result.value) {
        this.unLock(item);
      }
    });
  }

  unLock(obj) {
    var request = {
      user: this.user.id,
      datas: [{id:obj}],
    };

    this.busyGet = this.restClient
      .post(this.endPoint + 'unBlockUser', request)
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


  /*filterItems() {
    if (!this.searchTerm || this.searchTerm === "") {
      this.items = this.itemsTampon;
      return;
    }
  
    const searchTermLower = this.searchTerm.toLowerCase();
  
    this.items = this.items.filter(item => {
      const userLogin = (item.login || "").toLowerCase();
      const numero = item.contact || "";
      const nom= (item.nom || "").toLowerCase()
      const prenoms= (item.prenom || "").toLowerCase()
      const profil= (item.profilLibelle || "").toLowerCase()
      const categorie= (item.categoryLibelle || "").toLowerCase()
      const statu= (searchTermLower === "Bloqué".toLowerCase() ? true : searchTermLower === "Actif".toLowerCase() ? false : null)

      console.log("statu", statu)
  
      return (
        userLogin.includes(searchTermLower) ||
        numero.includes(this.searchTerm) ||
        nom.includes(searchTermLower) ||
        prenoms.includes(searchTermLower) ||
        profil.includes(searchTermLower) ||
        categorie.includes(searchTermLower)
        //(statu === null || item.locked === statu)
      );
    });
  
    console.log("tab filtered", this.items);
    // return this.items
  }*/


  filterItems() {
    if (!this.searchTerm || this.searchTerm === "") {
      this.items = this.itemsTampon;
      return;
    }
  
    const searchTermLower = this.searchTerm.toLowerCase();
  
    this.items = this.itemsTampon.filter(item => {
      const userLogin = (item.login || "").toLowerCase();
      const numero = item.contact || "";
      const nom = (item.nom || "").toLowerCase();
      const prenoms = (item.prenom || "").toLowerCase();
      const profil = (item.profilLibelle || "").toLowerCase();
      const categorie = (item.categoryLibelle || "").toLowerCase();
      
      let isMatch = false;
  
      if (searchTermLower === "bloqué") {
        isMatch = item.locked === true;
      } else if (searchTermLower === "actif") {
        isMatch = item.locked === false;
      } else {
        isMatch = (
          userLogin.includes(searchTermLower) ||
          numero.includes(this.searchTerm) ||
          nom.includes(searchTermLower) ||
          prenoms.includes(searchTermLower) ||
          profil.includes(searchTermLower) ||
          categorie.includes(searchTermLower)
        );
      }
  
      return isMatch;
    });
  
    console.log("tab filtered", this.items);
    // return this.items
  }
  

  ngOnInit() {
    this.getItems();
    this.resetForm();
    this.getItemsProfile();
    this.getItemsCivilite();
    this.getItemsCategory();
  }
}
