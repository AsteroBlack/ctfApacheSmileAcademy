import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/service/auth.service';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { BsModalRef } from 'ngx-bootstrap/modal';
import Swal from 'sweetalert2';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import { ResponseTypeNumero, TypeNumeroLabel } from 'src/app/core/models/type-numero.model';
import { TypeNumeroService } from 'src/app/core/service/type-numero.service';
import { MachineInfoService } from 'src/app/core/service/machine-info.service';
import { map, tap, switchMap, catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import * as moment from 'moment';

@Component({
  selector: 'app-recherche-num-unique',
  templateUrl: './recherche-num-unique.component.html',
  styleUrls: ['./recherche-num-unique.component.scss']
})
export class RechercheNumUniqueComponent implements OnInit {

  itemToSave: any = {};
  user: any = {};
  title = "Recherche numéro unique";
  items: Array<any> = [];
  modalRef: BsModalRef;
  fileOltImport = {};
  currentPage: number = 1;
  itemsPerPage: number = 10;
  totalItems: number;
  busyGet: Subscription;
  busySave: Subscription;
  loading: boolean = false;
  endPoint: string = 'actionUtilisateur/';
  itemToSearch: any = {};
  private typeNumeros: ResponseTypeNumero[]
  response: {ipAdress: string, machine: string}= {
    ipAdress: "",
    machine: ""
  }
  searchData: any = {
    start: '',
    end: '',
  };

 
  

  constructor(
    public bsModalRef: BsModalRef,
    private authService: AuthService,
    private restClient: RestClientService,
    private utilities: UtilitiesService,
    private typeNumeroService: TypeNumeroService,
    private machineInfoService: MachineInfoService
  ) {
    this.user = this.authService.currentUserValue;
  }
  pageChanged(event: any): void {
    this.currentPage = event.page;
    this.getItems();
  }

  onPeriodeEmitted(event) {
    console.log('event', event);
    this.searchData.start = moment(event.start).format('DD/MM/YYYY HH:mm:ss');
    this.searchData.end = moment(event.end).format('DD/MM/YYYY HH:mm:ss');
    console.log('start', this.searchData.start);
    console.log('end', this.searchData.end);
    //this.getItems(this.searchData)
  }


  // getTypeNumeros() {
  //   if (this.typeNumeros === undefined) {
  //     return this.typeNumeroService.getList().pipe(
  //       tap(response => {
  //         if(response.length>=2){
  //           this.typeNumeros = response;
  //           console.log("typeNum", this.typeNumeros)
  //         }
  //         else{
  //           throw new Error("less than 2 type numeros finded whereas need at least 2 type numeros for the next requests")
  //         }
  //       })
  //     )
  //   }
  //   return of([])
  // }

  getTypeNumeros(){
    if (this.typeNumeros === undefined) {
      this.typeNumeroService.getList().subscribe(
        {
          next: (res: any) =>{
            if(res && res.length >= 2){
              this.typeNumeros= res
              console.log("typeNum", this.typeNumeros)
            }
            else{
              throw new Error("less than 2 type numeros finded whereas need at least 2 type numeros for the next requests")
            }
          }
        }
      ) 
  }
}

  getTypeNumero(label: TypeNumeroLabel) {
    return this.typeNumeros.find(typeNumero => typeNumero.libelle === label);
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



 
  /*getItems(param?: any) {
    
    if(this.itemToSearch.numero.length < 10){
      this.utilities.showNotification(
        'snackbar-danger',
        'Veuillez saisir un contact à 8 chiffres svp !',
        'bottom',
        'center'
      );
      return;
    }

    const reqSecond = {
      user: this.user.id,
      msisdn: this.itemToSearch.numero ? this.itemToSearch.numero : ""
    };

    const reqPrime= {
      user: this.user.id,
      data: {
        typeNumeroId: this.getTypeNumero(TypeNumeroLabel.BSCS).id,
        numero: this.itemToSearch.numero ? this.itemToSearch.numero : "",
        createdAt: param?.start ? param.start : moment().format('DD/MM/YYYY HH:mm:ss'),
        createdAtParam: {
          operator: "[]",
          start: this.searchData?.start ? this.searchData.start : moment().format('DD/MM/YYYY HH:mm:ss'),
          end: this.searchData?.end ? this.searchData.end : moment().format('DD/MM/YYYY HH:mm:ss'),
        }
      },
      index: this.currentPage - 1,
      size: this.itemsPerPage,

    }

    this.busyGet = this.restClient
      .post(this.endPoint + 'getByCriteria', reqPrime)
      .pipe(
        tap((res) => {
          if (!res['hasError'] && res['items']) {
            this.items= res['items'] ;
            //this.totalItems = res['count'];
          } else {
            this.utilities.showNotification(
              'snackbar-danger',
              this.utilities.formatMsgServeur(res['message']),
              'bottom',
              'center'
            );
            this.items= []
            this.totalItems = 0;
          }
        }),
        switchMap(() => this.restClient.post(this.endPoint + 'findByMsisdn', reqSecond))
      )
      .subscribe(
        {
          next: (res) => {
            if (res && res['data']['data']) {
              if(this.items.length < 1){
                this.items= [(res['data']['data'])]
              }  
              console.log("findByMsidn", this.items)
              //this.totalItems = res['count'];
            } else {
              this.utilities.showNotification(
                'snackbar-danger',
                this.utilities.formatMsgServeur(res['message']),
                'bottom',
                'center'
              );
              this.items = [];
              this.totalItems = 0;
            }
          },
          error: (err) => console.info(err),
          complete: () => console.info('complete'),
        }  );
  }*/


  getItems(){
    if(this.itemToSearch.numero.length < 10){
      this.utilities.showNotification(
        'snackbar-danger',
        'Veuillez saisir un contact à 8 chiffres svp !',
        'bottom',
        'center'
      );
      return;
    }

    const req = {
      user: this.user.id,
      numbers: [this.itemToSearch.numero ? this.itemToSearch.numero : ""]
    };

    this.restClient.post(this.endPoint + 'findByMsisdn', req)
      .subscribe(
        {
          next: (res) => {
            if (res && res['data']) {
              this.items= res['data']  
              console.log("findByMsidn", this.items)
              //this.totalItems = res['count'];
            } else {
              this.utilities.showNotification(
                'snackbar-danger',
                this.utilities.formatMsgServeur(res['message']),
                'bottom',
                'center'
              );
              this.items = [];
              this.totalItems = 0;
            }
          },
          error: (err) => console.info(err),
          complete: () => console.info('complete'),
        }
      )
  }


  actionSurNumero(item, val) {
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

  demande(obj: any, val?: any, motif?: any) {
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


  validate(param, type){
    let request = {
      user: this.user.id,
      ipAddress: this.response.ipAdress,
      hostName: this.response.machine,
      datas: [{
        numero: param.msisdn,
        typeNumeroId: this.getTypeNumero(TypeNumeroLabel.CORAIL).id,
        idStatus: type,
      }],
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
              this.getItems()
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
              this.getItems()
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
          item= item.map(param =>{
            /*const objet= {
              numero: param.numero,
              typeNumeroId: this.getTypeNumero(TypeNumeroLabel.CORAIL).id
            }*/

            const objet= {
              idStatus: val,
              numero: param.msisdn,
              motif: result.value ? result.value : "",
              portNumber: param.portNumber,
              contractId: param.contractId,
              offerName: param.offerName,
              activationDate: param.activationDate,
              statusBscs: item.status,
              isTransfert: true,
              typeNumeroId: this.getTypeNumero(TypeNumeroLabel.CORAIL).id,
            }
            
            return objet
          })
          //this.transfertCorbeille(item)
          this.demande(item)
        }else{
          /*const obj= {
            numero: item.numero,
            typeNumeroId: item.typeNumeroId
          }*/

          const obj= {
            idStatus: val,
            numero: item.msisdn,
            motif: result.value ? result.value : "",
            portNumber: item.portNumber,
            contractId: item.contractId,
            offerName: item.offerName,
            activationDate: item.activationDate,
            statusBscs: item.status,
            isTransfert: true,
            typeNumeroId: this.getTypeNumero(TypeNumeroLabel.CORAIL).id,
          }
          //this.transfertCorbeille([obj])
          this.demande([obj])
        }
        //this.transfertCorbeille(item)
      }
    })
  }


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
              numero: param.msisdn,
              motif: result.value ? result.value : "",
              portNumber: param.portNumber,
              contractId: param.contractId,
              offerName: param.offerName,
              activationDate: param.activationDate,
              statusBscs: item.status,
              isTransfert: true,
              typeNumeroId: this.getTypeNumero(TypeNumeroLabel.CORAIL).id,
            }
            
            return objet
          })
          this.demande(item)
        }else{

          const obj= {
            idStatus: val,
            numero: item.msisdn,
            motif: result.value ? result.value : "",
            portNumber: item.portNumber,
            contractId: item.contractId,
            offerName: item.offerName,
            activationDate: item.activationDate,
            statusBscs: item.status,
            isTransfert: true,
            typeNumeroId: this.getTypeNumero(TypeNumeroLabel.CORAIL).id,
          }
          this.demande([obj])
        }
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
        this.validate(item, val)
      }
    })
  }

  demandeMiseEnMachine(item,val){
    Swal.fire({
      title: 'Mise en Machine',
      text:`Etes vous sure d'effectuer une mise en machine ?`,
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
        this.validate(item, val)
      }
    })
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

  deblocage(item,val){
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
    
  }



  ngOnInit(): void {
    this.response.machine= this.machineInfoService.getMachineName()
    console.log("machine name", this.machineInfoService.getMachineName())
    this.getIpAdress()
    this.getTypeNumeros()
    this.getItems()
  }

}
