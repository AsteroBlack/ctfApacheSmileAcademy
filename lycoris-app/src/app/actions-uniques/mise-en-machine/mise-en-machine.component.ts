import { Component, OnInit, TemplateRef } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/service/auth.service';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import Swal from 'sweetalert2';
import * as moment from 'moment';
import * as _ from 'lodash';
import { ModalPreviewImportComponent } from 'src/app/num-sensible/modal-preview-import/modal-preview-import.component';
import { RechercheNumUniqueComponent } from 'src/app/num-sensible/recherche-num-unique/recherche-num-unique.component';
import { RechercheNumeroComponent } from 'src/app/num-sensible/recherche-numero/recherche-numero.component';
import { ResponseTypeNumero, TypeNumeroLabel } from 'src/app/core/models/type-numero.model';
import { tap, switchMap } from 'rxjs/operators';
import { TypeNumeroService } from 'src/app/core/service/type-numero.service';
import { MachineInfoService } from 'src/app/core/service/machine-info.service';
import { of } from 'rxjs';

@Component({
  selector: 'app-mise-en-machine',
  templateUrl: './mise-en-machine.component.html',
  styleUrls: ['./mise-en-machine.component.scss']
})
export class MiseEnMachineComponent implements OnInit {

  items: Array<any> = [];
  itemsTampon: Array<any>= []
  searchTerm: string= ""
  itemToSave: any = {};
  modalRef: BsModalRef;
  user: any = {};
  itemToSearch: any = {};
  currentPage: number = 0;
  itemsPerPage: number = 10;
  totalItems: number;
  busyGet: Subscription;
  busySave: Subscription;
  loading: boolean = false;
  endPoint: string = 'actionUtilisateur/';
  endPointIdStatu: string= 'status/'
  statusId: Array<any>= []
  statuDebloque: any
  statutBloque: any
  bsValue: Date;
  click: boolean = false;
  searchData: any = {
    start: '',
    end: '',
  };
  etatImport: any;
  isOn: boolean = false;
  isOnUnBlocked: boolean= false
  isActiveDate:boolean= false
  filePath= ""
  private typeNumeros: ResponseTypeNumero[]
  response: {ipAdress: string, machine: string}= {
    ipAdress: "",
    machine: ""
  }
  datasValidateDem: Array<{numero: any, typeNumeroId: any, idStatus: any, id: any}>= []
  datasCreateDem: Array<{
    idStatus?: any,
    id?: any,
    numero?: any,
    motif?: any,
    portNumber?: any,
    contractId?: any,
    offerName?: any,
    activationDate?: any,
    statusBscs?: any,
    typeNumeroId?: any,
    isChecked?: false
  }>= []
  clicked= false
  valeurVide= 'N/A'

  constructor(
    private authService: AuthService,
    private restClient: RestClientService,
    private utilities: UtilitiesService,
    private modalService: BsModalService,
    private typeNumeroService: TypeNumeroService,
    private machineInfoService: MachineInfoService
  ) {
    this.user = this.authService.currentUserValue;
    this.etatImport = JSON.parse(localStorage.getItem('EtatImport') || '{}');
    console.log(this.etatImport?.etat);
  }


  pageChanged(event: any): void {
    this.currentPage = event.pageIndex;
    this.itemsPerPage= event.pageSize
    if(!this.isActiveDate){
      this.isOnUnBlocked ? this.onChangeToggle(this.statuDebloque[0]['id']) : this.onChangeToggle(this.statutBloque[0]['id'])
    }else this.getItemsAll(this.searchData);
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

  funEtatImport(){
    if (this.etatImport.date != moment().format('DD/MM/YYYY')){
      localStorage.setItem("EtatImport",JSON.stringify({etat: '0', date: moment().format('DD/MM/YYYY')}));
    }
  }

  resetForm() {
    this.itemToSave.numero = '';
  }

  exportData() {
    let request = {
      user: this.user.id,
      ipAddress: this.response.ipAdress,
      hostName: this.response.machine,
      data: {
        createdAt: this.searchData?.start ? this.searchData?.start : moment().format('DD/MM/YYYY HH:mm:ss'),
        createdAtParam: {
          operator: '[]',
          start: this.searchData?.start ? this.searchData?.start : moment().format('DD/MM/YYYY HH:mm:ss'),
          end: this.searchData?.end ? this.searchData?.end : moment().format('DD/MM/YYYY HH:mm:ss'),
        }
      },
    };

    this.busyGet = this.restClient.post('actionUtilisateur/exportActionUtilisateur', request).subscribe(
      (res) => {
        if (res && res.filePathDoc) {
         res.filePath
          window.location.href = res ? res.filePathDoc : window.location.href;
          this.utilities.showNotification(
              'snackbar-success',
              this.utilities.formatMsgServeur(res['status']['message']),
              'bottom',
              'center'
            );
        } else {
          this.utilities.showNotification(
                'snackbar-danger',
                this.utilities.formatMsgServeur(res['status']['message']),
                'bottom',
                'center'
              );
        }
      },
      (err) => {
        console.log('Error occured', err);
      }
      );
  }

  getItemsAll(param?: any) {
    let request = {
      user: this.user.id,
      data: {
        numero: this.itemToSearch.numero ? this.itemToSearch.numero : null,
        updatedAt: param?.start ? param.start : moment().format('DD/MM/YYYY HH:mm:ss'),
        updatedAtParam: {
          operator: '[]',
          start: param?.start ? param.start : moment().format('DD/MM/YYYY HH:mm:ss'),
          end: param?.end ? param.end : moment().format('DD/MM/YYYY HH:mm:ss'),
        }
      },
      index: this.currentPage,
      size: this.itemsPerPage,
    };
    //console.log("user id", this.user.id)
    this.busyGet = this.restClient
      .post(this.endPoint + 'getByCriteria', request)
      .subscribe(
        {
          next: (res) => {
            if (res && res['items']) {
              this.items = res['items'];
              this.totalItems = res['count'];
            } else {
              this.items = [];
              this.totalItems = 0;
            }
          },
          error: (err) => console.info(err),
          complete: () => console.info('complete'),
        }  );
  }

  getTypeNumeros() {
    if (this.typeNumeros === undefined) {
      return this.typeNumeroService.getList().pipe(
        tap(response => {
          if(response.length>=2){
            this.typeNumeros = response;
            console.log("typeNum", this.typeNumeros)
          }
          else{
            throw new Error("less than 2 type numeros finded whereas need at least 2 type numeros for the next requests")
          }
        })
      )
    }
    return of([])
  }

  getTypeNumero(label: TypeNumeroLabel) {
    return this.typeNumeros.find(typeNumero => typeNumero.libelle === label);
  }


  getItems(typeNumLabel: TypeNumeroLabel, idStatu?: any){
    let request= {
      user: this.user.id,
      isSimpleLoading: false,
      data: {
        id: "",
        date: "",
        createdAt: "",
        createdBy: "",
        updatedAt: "",
        updatedBy: "",
        isDeleted: "",
        idStatus: idStatu,
        idUser: "",
        numero: "",
        motif: "",
        empreinte: "",
        idProfil: "",
        isExcelNumber: "",
        statut: "",
        idCategory: "",
        isMachine: "",
        lastMachineDate: "",
        lastDebloquage: "",
        statusBscs: "",
        activationDate: "",
        offerName: "",
        fromBss: "",
        typeNumeroId: null
      },
      index: this.currentPage,
      size: this.itemsPerPage,
    }

    this.busyGet = this.getTypeNumeros()
      .pipe(
        tap(() => {
          request.data.typeNumeroId= this.getTypeNumero(typeNumLabel).id
        }),
        switchMap(() => this.restClient.post(this.endPoint + 'getByCriteria', request))
      )
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
        }  );
  }

  getIdStatus(){
    const req= {
      user: this.user.id,
      isSimpleLoading: false,
      data: {
        id: "",
        code: "",
        createdAt: "",
        createdBy: "",
        updatedAt: "",
        updatedBy: "",
        isDeleted: ""
      }
    }

    this.busyGet= this.restClient.post(this.endPointIdStatu + 'getByCriteria', req)
      .subscribe(
        {
          next: (res) =>{
            if(res && !res['hasError']){
              this.statusId= res['items']
              this.statuDebloque= this.statusId.filter((item) => item.code == 'DEBLOQUE')
              this.statutBloque= this.statusId.filter((item) => item.code == 'BLOQUE')
              console.log("Id debloque", this.statuDebloque[0]['id'])
            }else this.statusId= []
          },
          error: (err) => console.log(err),
          complete: () => console.info('complete')
        }
      )
  }

  getIpAdress(){
    this.busyGet= this.restClient.getIpAdress()
      .subscribe(
        {
          next: (res) =>{
            if(res){
              //console.log("res IP", res.IPv4)
              this.response.ipAdress= res.IPv4
              console.log("res IP", this.response.ipAdress)
            }else return
          },
          error: (err) => console.log(err)
        }
      )
  }

   onChangeToggle(idStatu?: any){
     //this.isOn ? this.getItems(TypeNumeroLabel.CORAIL, idStatu) : this.getItems(TypeNumeroLabel.BSCS, idStatu)
     this.getItems(TypeNumeroLabel.CORAIL, idStatu)
     this.isActiveDate ? this.isActiveDate= !this.isActiveDate : ""
     console.log("toggle")
     this.datasCreateDem= []
   }

  onChangeToggleIdUnBloque(){
    this.isOnUnBlocked ? this.onChangeToggle(this.statuDebloque[0]['id']) : this.onChangeToggle(this.statutBloque[0]['id'])
    console.log('toogle debloque')
  }


  filterItems() {
    if (!this.searchTerm || this.searchTerm === "") {
      this.items = this.itemsTampon;
      return;
    }
  
    const searchTermLower = this.searchTerm.toLowerCase();
  
    this.items = this.items.filter(item => {
      const userLogin = (item.userLogin || "").toLowerCase();
      const numero = item.numero || "";
      const categoryCode = (item.categoryCode || "").toLowerCase();
      const statusCode = (item.statusCode || "").toLowerCase();
      const contractId= (item.contractId || "").toLowerCase()
      const offerName= (item.offerName || "").toLowerCase()
      const portNumber= (item.portNumber || "").toLowerCase()
      const serialNumber= (item.serialNumber || "").toLowerCase()
      const statuBscs= (item.statusBscs || "").toLowerCase()
      const statuMachine= ("PAS EN MACHINE" || "MISE EN MACHINE").toLowerCase()
  
      return (
        userLogin.includes(searchTermLower) ||
        numero.includes(this.searchTerm) ||
        categoryCode.includes(searchTermLower) ||
        statusCode.includes(searchTermLower) ||
        contractId.includes(searchTermLower) ||
        offerName.includes(searchTermLower) ||
        portNumber.includes(searchTermLower) ||
        serialNumber.includes(searchTermLower) ||
        statuBscs.includes(searchTermLower) ||
        statuMachine.includes(searchTermLower)
      );
    });
  
    console.log("tab filtered", this.items);
    // return this.items
  }
  


  onPeriodeEmitted(event) {
    this.filePath= ""
    console.log('event', event);
    this.searchData.start = moment(event.start).format('DD/MM/YYYY HH:mm:ss');
    this.searchData.end = moment(event.end).format('DD/MM/YYYY HH:mm:ss');
    console.log('start', this.searchData.start);
    console.log('end', this.searchData.end);
    if(this.isActiveDate)this.getItemsAll(this.searchData);
  }

  /*actionSurNumero(item, val) {
    Swal.fire({
      title: 'Action sur numéro',
      text: 'Vous êtes sur le point de mener une action sur le numéro. Voulez-vous poursuivre cette action ?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non',
    }).then((result) => {
      if (result.value) {
        this.action(item,val);
      }
    });
  }

  action(obj, val,motif?) {
    let request = {
      user: this.user.id,
      datas: [
        {
          idStatus: val,
          numero: obj.numero,
          motif: motif ? motif :"",
      }
      ],
    };

    this.busyGet = this.restClient
      .post(this.endPoint + 'blocageNumero', request)
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
*/
  demande(obj, val,motif?) {
    // let request = {
    //   user: this.user.id,
    //   ipAddress: this.response.ipAdress,
    //   hostName: this.response.machine,
    //   datas: [
    //     {
    //       idStatus: val,
    //       numero: obj.numero,
    //       motif: motif ? motif :"",
    //       portNumber: obj.portNumber,
    //       contractId: obj.contractId,
    //       offerName: obj.offerName,
    //       activationDate: obj.activationDate,
    //       statusBscs: obj.statusBscs,
    //       typeNumeroId: obj.typeNumeroId
    //     }
    //   ],
    // };

    let request = {
      user: this.user.id,
      ipAddress: this.response.ipAdress,
      hostName: this.response.machine,
      datas: obj
    };

    this.busyGet = this.restClient
      .post('demande/create', request)
      .subscribe(
        (res) => {
          console.log(res);
          if (!res['hasError']) {
            this.utilities.showNotification(
              'snackbar-success',
              this.utilities.formatMsgServeur('Demande effectuée'),
              'bottom',
              'center'
            );
            this.currentPage = 1;
            //this.getItemsAll(this.searchData);
            if(!this.isActiveDate){
              this.isOnUnBlocked ? this.onChangeToggle(this.statuDebloque[0]['id']) : this.onChangeToggle(this.statutBloque[0]['id'])
            }else this.getItemsAll(this.searchData);
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


  validate(param, type){
    // let request = {
    //   user: this.user.id,
    //   ipAddress: this.response.ipAdress,
    //   hostName: this.response.machine,
    //   datas: [{
    //     numero: param.numero,
    //     typeNumeroId: param.typeNumeroId,
    //     idStatus: type,
    //     id: param.id
    //   }],
    // };

    let request = {
      user: this.user.id,
      ipAddress: this.response.ipAdress,
      hostName: this.response.machine,
      datas: param
    };

    this.busyGet = this.restClient
      .post('demande/actionOnNumber', request)
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
              //this.getItems(this.searchData);
              if(!this.isActiveDate){
                this.isOnUnBlocked ? this.onChangeToggle(this.statuDebloque[0]['id']) : this.onChangeToggle(this.statutBloque[0]['id'])
              }else this.getItemsAll(this.searchData);
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



  transfertCorbeille(param?: any){
    const req= {
      user: this.user.id,
      datas: param
    }


    this.busyGet = this.restClient
      .post(this.endPoint + 'create', req)
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
              //this.getItems(this.searchData);
              if(!this.isActiveDate){
                this.isOnUnBlocked ? this.onChangeToggle(this.statuDebloque[0]['id']) : this.onChangeToggle(this.statutBloque[0]['id'])
              }else this.getItemsAll(this.searchData);
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



  setCheckedItem(data: any, event?: Event){

    console.log("event", event)
    console.log("item", data)
    const index= this.datasCreateDem.indexOf(data)
    if(data.isChecked){
      this.datasCreateDem.push(data)
    }else this.datasCreateDem.splice(index, 1)

    console.log("datas", this.datasCreateDem)
  }

  checkAuthority(data: Array<any>, type: number){
    let hasAuthorithy: boolean
    if(type === 1){
      hasAuthorithy= data.some(item => item.typeNumeroLibelle === 'CORAIL' && item.statusCode !== 'BLOQUE')
      console.log("bloque", data)
    }
    else if(type === 2){
      hasAuthorithy= data.some(item => item.typeNumeroLibelle === 'CORAIL' && item.statusCode !== 'DEBLOQUE')
      console.log("debloque", data)
    }
    else if(type === 3){
      hasAuthorithy= data.some(item => item.typeNumeroLibelle === 'CORAIL' && !item.isMachine)
      console.log("machine", data)
    }

    return hasAuthorithy
  }



 /* deblocage(item,val){
    Swal.fire({
      title: 'Saisir le motif de déblocage',
      input: 'textarea',
      inputAttributes: {
        autocapitalize: 'off'
      },
      showCancelButton: true,
      confirmButtonText: 'Valider',
      cancelButtonText: 'Annuler',
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      showLoaderOnConfirm: true,
    }).then((result) => {
      if (result.isConfirmed) {
        console.log("eee",result.value);
        this.action(item,val,result.value);
      }
    })

  } */

  demandeDeblocage(item,val){
    Swal.fire({
      title: 'Saisir le motif de déblocage',
      input: 'textarea',
      inputAttributes: {
        autocapitalize: 'off'
      },
      showCancelButton: true,
      confirmButtonText: 'Valider',
      cancelButtonText: 'Annuler',
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      showLoaderOnConfirm: true,
    }).then((result) => {
      if (result.isConfirmed) {
        console.log("eee",result.value);
        if(Array.isArray(item)){
          item= item.map(param =>{
            const objet= {
              idStatus: val,
              numero: param.numero,
              motif: result.value ? result.value : "",
              portNumber: param.portNumber,
              contractId: param.contractId,
              offerName: param.offerName,
              activationDate: param.activationDate,
              statusBscs: param.statusBscs,
              isTransfert: false,
              typeNumeroId: param.typeNumeroId,
              idTypeCategory: param.idCategory
            }
            
            return objet
          })
          this.demande(item, val)
        }else{
          const obj= {
            idStatus: val,
            numero: item.numero,
            motif: result.value ? result.value : "",
            portNumber: item.portNumber,
            contractId: item.contractId,
            offerName: item.offerName,
            activationDate: item.activationDate,
            statusBscs: item.statusBscs,
            isTransfert: false,
            typeNumeroId: item.typeNumeroId,
            idTypeCategory: item.idCategory
          }
          this.demande([obj], val)
        }
        //this.demande(item,val,result.value);
      }
    })
  }

  demandeBlocage(item,val){
    Swal.fire({
      title: 'Blocage',
      text: `Etes vous sure d'effectuer un blocage ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Valider',
      cancelButtonText: 'Annuler',
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      showLoaderOnConfirm: true,
    }).then((result) => {
      if (result.isConfirmed) {
        console.log("eee",result.value);
        //this.demande(item,val,result.value);
        if(Array.isArray(item)){
          item= item.map(param =>{
            const objet= {
              numero: param.numero,
              typeNumeroId: param.typeNumeroId,
              idStatus: val,
              id: param.id
            }
            
            return objet
          })
          this.validate(item, val)
        }else{
          const obj= {
            numero: item.numero,
            typeNumeroId: item.typeNumeroId,
            idStatus: val,
            id: item.id
          }
          this.validate([obj], val)
        }
        //this.validate(item, val)
      }
    })
  }

  demandeMiseEnMachine(item: any,val){
    Swal.fire({
      title: 'Mise en Machine',
      text: `Etes vous sure d'effectuer une mise en machine ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Valider',
      cancelButtonText: 'Annuler',
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      showLoaderOnConfirm: true,
    }).then((result) => {
      if (result.isConfirmed) {
        console.log("eee",result.value);
        //this.demande(item,val,result.value);
        if(Array.isArray(item)){
          item= item.map(param =>{
            const objet= {
              numero: param.numero,
              typeNumeroId: param.typeNumeroId,
              idStatus: val,
              id: param.id
            }

            return objet
          })
          this.validate(item, val)
        }else{
          const obj= {
            numero: item.numero,
            typeNumeroId: item.typeNumeroId,
            idStatus: val,
            id: item.id
          }
          this.validate([obj], val)
        }
        //this.validate(item, val)
      }
    })
  }

  demandeTransfertCorb(item?: any, val?: any){
    Swal.fire({
      title: 'Transfert de corbeille',
      input: 'textarea',
      inputAttributes: {
        autocapitalize: 'off'
      },
      showCancelButton: true,
      confirmButtonText: 'Valider',
      cancelButtonText: 'Annuler',
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      showLoaderOnConfirm: true,
    }).then((result) => {
      if (result.isConfirmed) {
        console.log("eee",result.value);
        if(Array.isArray(item)){
          item= item.map(item =>{
            /*const objet= {
              numero: param.numero,
              typeNumeroId: param.typeNumeroId
            }*/

            const objet= {
              idStatus: val,
              numero: item.numero,
              motif: result.value ? result.value : "",
              portNumber: item.portNumber,
              contractId: item.contractId,
              offerName: item.offerName,
              activationDate: item.activationDate,
              statusBscs: item.statusBscs,
              isTransfert: true,
              typeNumeroId: item.typeNumeroId,
              idTypeCategory: item.idCategory
            }
            
            return objet
          })
          //this.transfertCorbeille(item)
          this.demande(item, val)
        }else{
          /*const obj= {
            numero: item.numero,
            typeNumeroId: item.typeNumeroId
          }*/

          const obj= {
            idStatus: val,
            numero: item.numero,
            motif: result.value ? result.value : "",
            portNumber: item.portNumber,
            contractId: item.contractId,
            offerName: item.offerName,
            activationDate: item.activationDate,
            statusBscs: item.statusBscs,
            isTransfert: true,
            typeNumeroId: item.typeNumeroId,
            idTypeCategory: item.idCategory
          }
          //this.transfertCorbeille([obj])
          this.demande([obj], val)
        }
        //this.transfertCorbeille(item)
      }
    })
  }

  openPreviewImport(data?:any){
    let modal:any
    modal = ModalPreviewImportComponent
    this.modalRef = this.modalService.show(modal, {
      id: 1,
      class: 'modal-custo',
      // data:boulangerie
    });
    if (data) {
      this.modalRef.content.currentData = data;
    }

    this.modalRef.onHide?.subscribe((res) => {
      console.log('hidden',res);
      this.getItemsAll(this.searchData);
      this.etatImport = JSON.parse(localStorage.getItem('EtatImport') || '{}');
      this.funEtatImport();
    });
  }

  openSearch(data?:any){
    let modal:any
    modal = RechercheNumeroComponent
    this.modalRef = this.modalService.show(modal, {
      id: 1,
      class: 'modal-custo',
      // data:boulangerie
    });
    if (data) {
      this.modalRef.content.currentData = data;
    }

    this.modalRef.onHide?.subscribe((res) => {
      console.log('hidden',res);

    });
  }


  openSearchNumUnique(data?: any){
    let modal:any
    modal = RechercheNumUniqueComponent
    this.modalRef = this.modalService.show(modal, {
      id: 1,
      class: 'modal-custo',
      // data:boulangerie
    });
    if (data) {
      this.modalRef.content.currentData = data;
    }

    this.modalRef.onHide?.subscribe((res) => {
      console.log('hidden',res);

    });
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
    this.resetForm();
    this.funEtatImport();
    this.response.machine= this.machineInfoService.getMachineName()
    console.log("machine name", this.machineInfoService.getMachineName())
    //this.onChangeToggle()
    this.getIpAdress()
    this.getIdStatus()
    setTimeout(() =>{
      this.onChangeToggleIdUnBloque()
    },2500)
    //this.getItemsBscs()

  }

}
