import { Component, OnInit, TemplateRef } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/service/auth.service';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import { MachineInfoService } from 'src/app/core/service/machine-info.service';
import Swal from 'sweetalert2';
import * as moment from 'moment';
import * as _ from 'lodash';
import { map, tap, switchMap, catchError } from 'rxjs/operators';

@Component({
  selector: 'app-all-demandes',
  templateUrl: './all-demandes.component.html',
  styleUrls: ['./all-demandes.component.scss']
})
export class AllDemandesComponent implements OnInit {

  items: Array<any> = [];
  itemToSave: any = {};
  modalRef: BsModalRef;
  user: any = {};
  nomComplet: any;
  searchTermNumero: any;
  searchTermContractId: any;
  searchTermStatutTransfert: any;
  searchTermOfferName: any;
  searchTermstatusCode: any;
  searchTermCategoryCde: any;
  searchTermLibCategory: any;
  searchTermtypeDemandeCode: any;
  searchTermtypeDemandeLibelle: any;
  searchTermDate: any;
  SearchName: any;



  critereRecherche: any;

  itemToSearch: any = {};
  filterTerm!: string;
  currentPage: number = 1;
  itemsPerPage: number = 10;
  totalItems: number;
  busyGet: Subscription;
  busySave: Subscription;
  loading: boolean = false;
  endPoint: string = 'user/';
  itemsProfiles: any;
  itemsCivilite: any;
  bsValue: Date;
  isActiveBscs:boolean= false
  isActiveCorail:boolean= false
  isActiveDate:boolean= false
  filePath= ""
  searchTerm: string= ""
  itemsTampon: Array<any>= []
  response: {ipAdress: string, machine: string}= {
    ipAdress: "",
    machine: ""
  } 

  searchData: any = {
    start: '',
    end: '',
  };
  intervalValue: any;

  constructor(
    private authService: AuthService,
    private restClient: RestClientService,
    private modalService: BsModalService,
    private utilities: UtilitiesService,
    private machineInfoService: MachineInfoService
  ) {
    this.user = this.authService.currentUserValue;

  }

  pageChanged(event: any): void {
    this.currentPage = event.page;
    this.getItems(this.searchData);
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

    if (!item || !item.idCategorie) {
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

  onPeriodeEmitted(event) {
    this.filePath= ""
    console.log('event', event);
    this.searchData.start = moment(event.start).format('DD/MM/YYYY HH:mm:ss' );
    this.searchData.end = moment(event.end).format('DD/MM/YYYY HH:mm:ss');
    console.log('start', this.searchData.start);
    console.log('end', this.searchData.end);
    if(this.isActiveDate)this.getItems(this.searchData);
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

  validateDemande(demande: any, type: string){
    Swal.fire({
      title: `${type === 'masse' ? 'Validation de la demande en masse' : 'Validation de la demande unitaire'}`,
      text: 'Vous êtes sur le point de valider la demande. Voulez-vous poursuivre cette action ?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non',
    }).then((result) => {
      if (result.value) {
        type === 'unitaire' ? this.validate(demande) : this.deBlocageMasse(demande);
      }
    });

  }

  refuserDemande(demande: any){
    Swal.fire({
      title: 'Refus de la demande',
      text: 'Vous êtes sur le point de refuser la demande. Voulez-vous poursuivre cette action ?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non',
    }).then((result) => {
      if (result.value) {
        this.refuser(demande);
      }
    });
  }

  validate(param){
    let request = {
      user: this.user.id,
      ipAddress: this.response.ipAdress,
      hostName: this.response.machine,
      datas: [{
        id: param.id,
        idTypeDemande: "3",
        isTransfert: param.isTransfert,
        typeNumeroId: param.typeNumeroId,
        idStatus: param.idStatus,
        idTypeCategory: param.idTypeCategory
      }],
    };

    this.busyGet = this.restClient
      .post('demande/validerRefuser', request)
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
              this.getItems(this.searchData);
              //this.getItems()
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


  deBlocageMasse(item, val?: any) {
    let request = {
      user: this.user.id,
      data:
        {
          identifiant: item.actionEnMasseDto?.identifiant,
          idStatus: item.idStatus,
          typeNumeroId: item.typeNumeroId,
          idTypeDemande: item.idTypeDemande,
          idDemande: item.idDemande,
          isTransfert: false
      }
      ,
    };

    this.busyGet = this.restClient
      .post('actionEnMasse/executeOnMasse', request)
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
            this.getItems();
           this.intervalValue =  setInterval(() => {
              this.TimeStamp(item)
            }, 5000);
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

  TimeStamp(item) {
    let request = {
      user: this.user.id,
      data:
        {
          identifiant: item.identifiant,
      }
      ,
    };

    this.busyGet = this.restClient
      .post('actionEnMasse/timeStamp', request)
      .subscribe(
        (res) => {
          console.log(res);
          if ((!res['hasError'] ) && (res['item'].isOk == true)) {
            this.utilities.showNotification(
              'snackbar-success',
              this.utilities.formatMsgServeur('Blocage effectué'),
              'bottom',
              'center'
            );
            clearInterval(this.intervalValue);
            this.getItems(this.searchData);
          } else if(!res['hasError']() && (res['item'].isOk == false)){
            if (res['status'] && res['status']['message']) {
              this.utilities.showNotification(
                'snackbar-danger',
                this.utilities.formatMsgServeur(res['status']['message']),
                'bottom',
                'center'
              );
            }

          } else{
            clearInterval(this.intervalValue);
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


  refuser(param){
    let request = {
      user: this.user.id,
      ipAddress: this.response.ipAdress,
      hostName: this.response.machine,
      datas: [{
        id: param.id,
        idTypeDemande: "2",
        isTransfert: param.isTransfert,
        typeNumeroId: param.typeNumeroId,
        idStatus: param.idStatus,
        idTypeCategory: param.idTypeCategory
      }],
    };

    this.busyGet = this.restClient
      .post('demande/validerRefuser', request)
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
              this.getItems(this.searchData);
              //this.getItems()
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

  getItems(param?: any) {
    let request = {
      user: this.user.id,
      data: {
        typeDemandeCode: this.itemToSearch.typeDemandeCode ? this.itemToSearch.typeDemandeCode : null,
        idTypeDemande: null,
        createdAt: param?.start ? param.start : moment().format('DD/MM/YYYY HH:mm:ss'),
        createdAtParam: {
          operator: '[]',
          start: param?.start ? param.start : moment().format('DD/MM/YYYY HH:mm:ss'),
          end: param?.end ? param.end : moment().format('DD/MM/YYYY HH:mm:ss'),
        }
      },
      index: this.currentPage - 1,
      size: this.itemsPerPage,
    };


    this.busyGet= this.getTypeDemande()
      .pipe(
        tap((res) => {
          if(!res['hasError'] && res['items']){
            const idDemande= res['items'].filter(item => item.code === 'INITIALISER')[0].id
            request.data.idTypeDemande= idDemande
            console.log('id_demande', idDemande)
          }
        }),
        switchMap(() => this.restClient.post('demande/getByCriteria', request))
      )
      .subscribe(
        {
          next: (res) => {
            if (res && res['items']) {
              this.items = res['items'];
              console.log("getItems :", this.items);
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
      )
  }

  getIpAdress(){
    this.busyGet= this.restClient.getIpAdress()
     .subscribe(
      {
        next: (res) =>{
          if(res){
            console.log("res IP", res.IPv4)
            this.response.ipAdress= res.IPv4
          }else return
        },
        error: (err) => console.log(err)
      }
     )
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


getTypeDemande(){
  const req= {
    user: this.user.id,
    isSimpleLoading: false,
    data: {
      id: "",
      code: "",
      libelle: "",
      updatedAt: "",
      createdAt: "",
      isDeleted: "",
      updatedBy: "",
      createdBy: ""
    }
  }

  return this.restClient.post("typeDemande/getByCriteria", req)
}


getDemande(param: any){
  let typeDemande= ""

  if(param.idStatus == 2 && !param.isTransfert){
    typeDemande= "DEBLOCAGE"
  }else if(param.idStatus == 4 && !param.isTransfert){
    typeDemande= "SUPPRESSION"
  }else if(param.isTransfert){
    typeDemande= "TRANSFERT"
  }

  return typeDemande
}

  // getItemsCorail(){
  //   this.isActiveCorail= !this.isActiveCorail
  //   if(this.isActiveBscs)this.isActiveBscs= !this.isActiveBscs
  //   let request= {
  //     user: this.user.id,
  //     isSimpleLoadin: false,
  //     data:{
  //       id: "",
  //       idUser: "",
  //       idTypeDemande: "",
  //       idStatus: "",
  //       numero: "",
  //       createdAt: "",
  //       updatedAt: "",
  //       isDeleted: "",
  //       createdBy: "",
  //       updatedBy: "",
  //       motif: "",
  //       offerName: "",
  //       activationDate: "",
  //       statusBscs: "",
  //       validatedTelco: "",
  //       validatedOmci: "",
  //       typeNumeroId: "97"
  //     }
  //   }
  //   if(this.isActiveCorail){
  //     this.busyGet = this.restClient
  //     .post('demande/getByCriteria', request)
  //     .subscribe(
  //       {
  //         next: (res) => {
  //           if (res && res['items']) {
  //             this.items = res['items'];
  //             this.totalItems = res['count'];
  //           } else {
  //             this.items = [];
  //             this.totalItems = 0;
  //           }
  //         },
  //         error: (err) => console.info(err),
  //         complete: () => console.info('complete'),
  //       }
  //     );
  //   }
  // }

  // getItemsBscs(){
  //   this.isActiveBscs= !this.isActiveBscs
  //   if(this.isActiveCorail)this.isActiveCorail= !this.isActiveCorail
  //   let request= {
  //     user: this.user.id,
  //     isSimpleLoadin: false,
  //     data:{
  //       id: "",
  //       idUser: "",
  //       idTypeDemande: "",
  //       idStatus: "",
  //       numero: "",
  //       createdAt: "",
  //       updatedAt: "",
  //       isDeleted: "",
  //       createdBy: "",
  //       updatedBy: "",
  //       motif: "",
  //       offerName: "",
  //       activationDate: "",
  //       statusBscs: "",
  //       validatedTelco: "",
  //       validatedOmci: "",
  //       typeNumeroId: "98"
  //     }
  //   }
  //   if(this.isActiveBscs){
  //     this.busyGet = this.restClient
  //     .post('demande/getByCriteria', request)
  //     .subscribe(
  //       {
  //         next: (res) => {
  //           if (res && res['items']) {
  //             this.items = res['items'];
  //             this.totalItems = res['count'];
  //           } else {
  //             this.items = [];
  //             this.totalItems = 0;
  //           }
  //         },
  //         error: (err) => console.info(err),
  //         complete: () => console.info('complete'),
  //       }
  //     );
  //   }
  // }

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
              this.getItems(this.searchData);
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

  /*
  filterItems() {
    if (!this.searchTerm || this.searchTerm === "") {
      this.items = this.itemsTampon;
      return;
    }
  
    const searchTermLower = this.searchTerm.toLowerCase().trim();
    console.log("saisie ", searchTermLower)
  
    this.items = this.items.filter(item => {
      const userLogin = (item.userLogin || "").toLowerCase();
      const numero = item.numero || "";
      const typeDemande= (item.typeDemandeCode || "").toLowerCase()
      const typeDemandeLibelle= (item.typeDemandeLibelle || "").toLowerCase()
      const nom= (item.userNom || "").toLowerCase()
      const prenom= (item.userPrenom || "").toLowerCase() 
      const statusCode = (item.statusCode || "").toLowerCase();
      const contractId= (item.contractId || "").toLowerCase()
      const offerName= (item.offerName || "").toLowerCase()
      const categoryCode= (item.categoryCode || "").toLowerCase()
      const categoryLibelle= (item.categoryLibelle || "").toLowerCase()

      let isMatch= false;

      if (searchTermLower === "oui" || searchTermLower === "non") {
        const searchTermBoolean = searchTermLower === "oui";
        const isTransfertMatch = item.isTransfert === searchTermBoolean;

        if (isTransfertMatch) {
          isMatch = true;
        }
      }else {
        isMatch= (
          userLogin.includes(searchTermLower) ||
          numero.includes(this.searchTerm) ||
          typeDemande.includes(searchTermLower) ||
          typeDemandeLibelle.includes(searchTermLower) ||
          statusCode.includes(searchTermLower) ||
          contractId.includes(searchTermLower) ||
          offerName.includes(searchTermLower) ||
          nom.includes(searchTermLower) ||
          prenom.includes(searchTermLower) ||
          categoryCode.includes(searchTermLower) ||
          categoryLibelle.includes(searchTermLower)
        );
      }
      console.log("match", isMatch)
      return isMatch
    });
  
    console.log("tab filtered", this.items);
    // return this.items
  }

  */


  filterItems() {
    if (!this.searchTerm || this.searchTerm === "") {
      this.items = this.itemsTampon;
      return;
    }

    const searchTermLower = this.searchTerm.toLowerCase().trim();
    console.log(searchTermLower);

    this.items = this.items.filter(item => {
      const userLogin = (item.userLogin || "").toLowerCase();
      const numero = (item.numero || "").toString().toLowerCase();
      const typeDemande = (item.typeDemandeCode || "").toLowerCase();
      const typeDemandeLibelle = (item.typeDemandeLibelle || "").toLowerCase();
      const nom = (item.userNom || "").toLowerCase();
      const prenom = (item.userPrenom || "").toLowerCase();
      const statusCode = (item.statusCode || "").toLowerCase();
      const contractId = (item.contractId || "").toLowerCase();
      const offerName = (item.offerName || "").toLowerCase();

      let isMatch = false;

      isMatch = (
        userLogin.includes(searchTermLower) ||
        numero.includes(searchTermLower) ||
        typeDemande.includes(searchTermLower) ||
        typeDemandeLibelle.includes(searchTermLower) ||
        statusCode.includes(searchTermLower) ||
        contractId.includes(searchTermLower) ||
        offerName.includes(searchTermLower) ||
        nom.includes(searchTermLower) ||
        prenom.includes(searchTermLower)
      );

      console.log("match", isMatch)
      return isMatch
    });

}


/* searchLogin() {
  const data = {
    user: this.user.id | 1,
        data: {
          userLogin: this.searchTerm,
        },        
        index: 0,
        size: 10
  }


  console.log("searchLogin: " + data.data.userLogin);
  console.log('La touche Entrée a été pressée!');

  this.busyGet = this.restClient.post('demande/getByCriteria', data)
    .subscribe(
      res => {
        if (res) {
          this.items = res['items'];
          this.totalItems = res['count'];
          console.log(res);
        }
        else {
          console.log(res['hasError'])
          console.log("error: ", res)
          this.items = [];
          this.totalItems = 0;
        }
      },
      err => {
      }
    );
}
*/


/* searchManyInput

searchLogin() {
  const data = {
        user: this.user.id | 1,
        data: {
          userLogin: this.searchTerm,
        },
        index: 0,
        size: 10
  }

  console.log("searchLogin: " + data.data.userLogin);

  this.busyGet = this.restClient.post('demande/getByCriteria', data)
    .subscribe(
      res => {
        if (res) {
          this.items = res['items'];
          this.totalItems = res['count'];
          console.log(res);
        }
        else {
          console.log(res['hasError'])
          console.log("error: ", res)
          this.items = [];
          this.totalItems = 0;
        }
      },
      err => {
        
      }
    );
}

searchByName() {
  let[nom, prenom] = this.nomComplet.split(' ');
  
  
  console.log("nom :" + nom);
  console.log("prenom :" + prenom);
  console.log(this.nomComplet);
  
  const data = {
        user: this.user.id | 1,
        data: { 
          userNom: nom,
           userPrenom: prenom
        },
        index: 0,
        size: 10
  }

  console.log("searchByName " + data.data.userNom);
  console.log("searchByName " + data.data.userPrenom);
  console.log("nom complet :" + this.nomComplet + "ou : ", data.data.userNom + data.data.userPrenom);

  this.busyGet = this.restClient.post('demande/getByCriteria', data)
    .subscribe(
      res => {
        if (res) {
          this.items = res['items'];
          this.totalItems = res['count'];
          console.log(res);
        }
        else {
          console.log(res['hasError'])
          console.log("error: ", res)
          this.items = [];
          this.totalItems = 0;
        }
      },
      err => {
        
      }
    );
}

searchByNumber(event: any) {

  this.searchTermNumero = event.target.value.replace(/[^0-9]/g, '');

  if(this.searchTermNumero.length > 10){
    this.searchTermNumero = this.searchTermNumero.slice(0, 10);
  }else{
  
  const data = {
        user: this.user.id | 1,
        data: { 
          numero: this.searchTermNumero,
        },
        index: 0,
        size: 10
  }

  console.log("searchByName " + data.data.numero);

  this.busyGet = this.restClient.post('demande/getByCriteria', data)
    .subscribe(
      res => {
        if (res) {
          this.items = res['items'];
          this.totalItems = res['count'];
          console.log(res);
        }
        else {
          console.log(res['hasError'])
          console.log("error: ", res)
          this.items = [];
          this.totalItems = 0;
        }
      },
      err => {
        
      }
    );
}
}

searchByIdContrat() {
  const data = {
        user: this.user.id | 1,
        data: { 
          contractId: this.searchTermContractId,
        },
        index: 0,
        size: 10
  }

  console.log("contractId :", this.searchTermContractId)

  this.busyGet = this.restClient.post('demande/getByCriteria', data)
    .subscribe(
      res => {
        if (res) {
          this.items = res['items'];
          this.totalItems = res['count'];
          console.log(res);
        }
        else {
          console.log(res['hasError'])
          console.log("error: ", res)
          this.items = [];
          this.totalItems = 0;
        }
      },
      err => {
        
      }
    );
}

searchByStatutTransfert() {


  const data = {
        user: this.user.id | 1,
        data: { 
          isTransfert: (this.searchTermStatutTransfert.toLowerCase() === "oui") ? true : (this.searchTermStatutTransfert.toLowerCase() === "non") ? false : ""
        },
        index: 0,
        size: 10
  }

  console.log("isTransfert :", this.searchTermStatutTransfert)
  console.log("isTransfert value: ", data.data.isTransfert)

  this.busyGet = this.restClient.post('demande/getByCriteria', data)
    .subscribe(
      res => {
        if (res) {
          this.items = res['items'];
          this.totalItems = res['count'];
          console.log(res);
        }
        else {
          console.log(res['hasError'])
          console.log("error: ", res)
          this.items = [];
          this.totalItems = 0;
        }
      },
      err => {
        
      }
    );
}

searchByOfferName() {
  const data = {
        user: this.user.id | 1,
        data: { 
          offerName: this.searchTermOfferName
        },
        index: 0,
        size: 10
  }

  console.log("offerName :", this.searchTermOfferName)

  this.busyGet = this.restClient.post('demande/getByCriteria', data)
    .subscribe(
      res => {
        if (res) {
          this.items = res['items'];
          this.totalItems = res['count'];
          console.log(res);
        }
        else {
          console.log(res['hasError'])
          console.log("error: ", res)
          this.items = [];
          this.totalItems = 0;
        }
      },
      err => {
        
      }
    );
}

searchByStatusCode() {
  const data = {
        user: this.user.id | 1,
        data: { 
          statusCode: this.searchTermstatusCode
        },
        index: 0,
        size: 10
  }

  console.log("statusCode :", this.searchTermstatusCode)

  this.busyGet = this.restClient.post('demande/getByCriteria', data)
    .subscribe(
      res => {
        if (res) {
          this.items = res['items'];
          this.totalItems = res['count'];
          console.log(res);
        }
        else {
          console.log(res['hasError'])
          console.log("error: ", res)
          this.items = [];
          this.totalItems = 0;
        }
      },
      err => {
        
      }
    );
}

searchByCategoryCode() {
  const data = {
        user: this.user.id | 1,
        data: { 
          categoryCode: this.searchTermCategoryCde
        },
        index: 0,
        size: 10
  }

  console.log("categoryCode :", this.searchTermCategoryCde)

  this.busyGet = this.restClient.post('demande/getByCriteria', data)
    .subscribe(
      res => {
        if (res) {
          this.items = res['items'];
          this.totalItems = res['count'];
          console.log(res);
        }
        else {
          console.log(res['hasError'])
          console.log("error: ", res)
          this.items = [];
          this.totalItems = 0;
        }
      },
      err => {
        
      }
    );
}

searchByLibCategory() {
  const data = {
        user: this.user.id | 1,
        data: { 
          categoryLibelle: this.searchTermLibCategory
        },
        index: 0,
        size: 10
  }

  console.log("categoryLibelle :", this.searchTermLibCategory)

  this.busyGet = this.restClient.post('demande/getByCriteria', data)
    .subscribe(
      res => {
        if (res) {
          this.items = res['items'];
          this.totalItems = res['count'];
          console.log(res);
        }
        else {
          console.log(res['hasError'])
          console.log("error: ", res)
          this.items = [];
          this.totalItems = 0;
        }
      },
      err => {
        
      }
    );
}

searchBytypeDemandeCode() {
  const data = {
        user: this.user.id | 1,
        data: { 
          typeDemandeCode: this.searchTermtypeDemandeCode
        },
        index: 0,
        size: 10
  }

  console.log("typeDemandeCode :", this.searchTermtypeDemandeCode)

  this.busyGet = this.restClient.post('demande/getByCriteria', data)
    .subscribe(
      res => {
        if (res) {
          this.items = res['items'];
          this.totalItems = res['count'];
          console.log(res);
        }
        else {
          console.log(res['hasError'])
          console.log("error: ", res)
          this.items = [];
          this.totalItems = 0;
        }
      },
      err => {
        
      }
    );
}

searchBytypeDemandeLibelle() {
  const data = {
        user: this.user.id | 1,
        data: { 
          typeDemandeLibelle: this.searchTermtypeDemandeLibelle
        },
        index: 0,
        size: 10
  }

  console.log("typeDemandeLibelle :", this.searchTermtypeDemandeLibelle)

  this.busyGet = this.restClient.post('demande/getByCriteria', data)
    .subscribe(
      res => {
        if (res) {
          this.items = res['items'];
          this.totalItems = res['count'];
          console.log(res);
        }
        else {
          console.log(res['hasError'])
          console.log("error: ", res)
          this.items = [];
          this.totalItems = 0;
        }
      },
      err => {
        
      }
    );
}

searchByDate() {
  const date = this.searchTermDate;
  const ChangeDate = date.split('-');
  const formattedDate = new Date(`${ChangeDate[2]}/${ChangeDate[1]}/${ChangeDate[0]}`)
  console.log(formattedDate);

  const data = {
        user: this.user.id | 1,
        data: { 
          createdAt: formattedDate
        },
        index: 0,
        size: 10
  }

  console.log("createdAt :", this.searchTermDate)

  this.busyGet = this.restClient.post('demande/getByCriteria', data)
    .subscribe(
      res => {
        if (res) {
          this.items = res['items'];
          this.totalItems = res['count'];
          console.log(res);
        }
        else {
          console.log(res['hasError'])
          console.log("error: ", res)
          this.items = [];
          this.totalItems = 0;
        }
      },
      err => {
        
      }
    );
}

*/


  ngOnInit() {
    this.response.machine= this.machineInfoService.getMachineName()
    console.log("machine ", this.machineInfoService.getMachineName() )
    this.getIpAdress()
    this.getItems()
  }

}
