import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/service/auth.service';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { BsModalRef } from 'ngx-bootstrap/modal';
import Swal from 'sweetalert2';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import {
  ResponseTypeNumero,
  TypeNumeroLabel,
} from 'src/app/core/models/type-numero.model';
import { TypeNumeroService } from 'src/app/core/service/type-numero.service';
import { MachineInfoService } from 'src/app/core/service/machine-info.service';
import { map, tap, switchMap, catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import * as moment from 'moment';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-recherche-numero',
  templateUrl: './recherche-numero.component.html',
  styleUrls: ['./recherche-numero.component.scss'],
})
export class RechercheNumeroComponent implements OnInit {
  itemToSave: any = {};
  user: any = {};
  title = 'Recherche numéro sensible';
  items: Array<any> = [];
  modalRef: BsModalRef;
  fileOltImport = {};
  currentPage: number = 0;
  itemsPerPage: number = 10;
  totalItems: number;
  busyGet: Subscription;
  busySave: Subscription;
  loading: boolean = false;
  endPoint: string = 'actionUtilisateur/';
  itemToSearch: any = {};
  optionsData = ['LockedNumbers', 'RetailerNumbers', 'UnlockedRetailerNumbers'];
  selectedOption = this.optionsData[0];
  private typeNumeros: ResponseTypeNumero[];
  response: { ipAdress: string; machine: string } = {
    ipAdress: '',
    machine: '',
  };
  searchData: any = {
    start: '',
    end: '',
  };
  filePath = '';
  isActiveDate: boolean = false;
  isOn: boolean = false;
  listeNumeros: any = [];
  numbersControl = new FormControl('');
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
    this.currentPage = event.pageIndex;
    this.itemsPerPage = event.pageSize;
    if (this.isActiveDate) {
      this.getItems(this.searchData);
    } else this.getItems();
  }

  exporterFichier() {
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

  onPeriodeEmitted(event) {
    console.log('event', event);
    console.log('start', this.searchData.start);
    console.log('end', this.searchData.end);
    if (this.isActiveDate) {
      this.searchData.start = moment(event.start).format('DD/MM/YYYY HH:mm:ss');
      this.searchData.end = moment(event.end).format('DD/MM/YYYY HH:mm:ss');
    }
    //this.getItems(this.searchData)
  }

  onChangeToggle(idStatu?: any) {
    //this.isOn ? this.getItems(TypeNumeroLabel.CORAIL, idStatu) : this.getItems(TypeNumeroLabel.BSCS, idStatu)
    this.isActiveDate ? (this.isActiveDate = !this.isActiveDate) : '';
    console.log('toggle');
  }

  addItems(): void {
    if (
      this.itemToSearch.numero &&
      !this.listeNumeros.includes(this.itemToSearch.numero)
    ) {
      this.listeNumeros.push(this.itemToSearch.numero); // Ajouter le nouvel élément à la liste
      this.itemToSearch.numero = ''; // Réinitialiser le modèle de saisie
      this.numbersControl.setValue([
        ...this.numbersControl.value,
        this.itemToSearch.numero,
      ]);
    }
    console.log('add', this.listeNumeros);
  }

  removeSelectedItems(): void {
    const selectedToppings = this.numbersControl.value; // Obtenir les éléments sélectionnés
    this.listeNumeros = this.listeNumeros.filter(
      (topping) => !selectedToppings.includes(topping)
    ); // Supprimer les éléments sélectionnés de la liste
    this.numbersControl.setValue([]); // Réinitialiser la sélection
    console.log('remove', this.listeNumeros);
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
      hasAuthorithy= data.some(item => item.typeNumeroLibelle === 'CORAIL' && item.statusCode !== 'DEBLOQUE' && !item.isUnlocked)
      console.log("debloque", data)
    }
    else if(type === 3){
      hasAuthorithy= data.some(item => item.typeNumeroLibelle === 'CORAIL' && !item.isMachine)
      console.log("machine", data)
    }
    else if(type === 4){
      hasAuthorithy= data.some(item => !item.isTransferred)
      console.log("transfert", data)
    }

    return hasAuthorithy
  }

  getTypeNumeros() {
    if (this.typeNumeros === undefined) {
      return this.typeNumeroService.getList().pipe(
        tap((response) => {
          if (response.length >= 2) {
            this.typeNumeros = response;
            console.log('typeNum', this.typeNumeros);
          } else {
            throw new Error(
              'less than 2 type numeros finded whereas need at least 2 type numeros for the next requests'
            );
          }
        })
      );
    }
    return of([]);
  }

  /*getTypeNumeros(){
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
}*/

  getTypeNumero(label: TypeNumeroLabel) {
    return this.typeNumeros.find((typeNumero) => typeNumero.libelle === label);
  }

  getIpAdress() {
    this.busyGet = this.restClient.getIpAdress().subscribe({
      next: (res) => {
        if (res) {
          //console.log("res IP", res.IPv4)
          this.response.ipAdress = res.IPv4;
          console.log('res IP', this.response.ipAdress);
        } else return;
      },
      error: (err) => console.log(err),
    });
  }

  getItems(param?: any) {
    /*let request = {
      user: this.user.id,
      msisdn: this.itemToSearch.numero ? this.itemToSearch.numero : "zzz",
      sensitiveNumberPack:  this.selectedOption,
      pageNumber: this.currentPage - 1,
      pageSize: this.itemsPerPage,
    };*/

    const request = {
      user: this.user.id,
      data: {
        typeNumeroId: null,
        numero: this.itemToSearch.numero ?? '',
        updatedAt: this.searchData?.start ? this.searchData.start : '',
        updatedAtParam: {
          operator: '[]',
          start: this.searchData?.start ? this.searchData.start : '',
          end: this.searchData?.end ? this.searchData.end : '',
        },
      },
      index: this.currentPage,
      size: this.itemsPerPage,
    };

    this.busyGet = this.getTypeNumeros()
      .pipe(
        tap(() => {
          request.data.typeNumeroId = this.getTypeNumero(
            TypeNumeroLabel.CORAIL
          ).id;
        }),
        switchMap(() =>
          this.restClient.post(this.endPoint + 'getByCriteria', request)
        )
      )
      .subscribe({
        next: (res) => {
          if (res && res['items']) {
            this.items = res['items'];
            this.totalItems = res['count'];
            this.filePath = res['filePathDoc'] ? res['filePathDoc'] : '';
          } else {
            this.items = [];
            this.totalItems = 0;
          }
        },
        error: (err) => console.info(err),
        complete: () => console.info('complete'),
      });
  }

  getListNumbers() {
    const req = {
      user: this.user.id,
      data: {
        datasNumero: this.listeNumeros,
        updatedAt: this.searchData?.start ? this.searchData.start : '',
        updatedAtParam: {
          operator: '[]',
          start: this.searchData?.start ? this.searchData.start : '',
          end: this.searchData?.end ? this.searchData.end : '',
        },
      },
    };

    this.busyGet = this.restClient
      .post(this.endPoint + 'getNumbersList', req)
      .subscribe({
        next: (res) => {
          if (res && res['items']) {
            this.items = res['items'];
            this.totalItems = res['count'];
            this.filePath = res['filePathDoc'] ? res['filePathDoc'] : '';
          } else {
            this.items = [];
            this.totalItems = 0;
          }
        },
        error: (err) => console.info(err),
        complete: () => console.info('complete'),
      });
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
        this.action(item, val);
      }
    });
  }

  demande(obj, val, motif?) {
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
      datas: obj,
    };

    this.busyGet = this.restClient.post('demande/create', request).subscribe(
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
          this.getListNumbers()
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

  validate(param, type) {
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
      datas: param,
    };

    this.busyGet = this.restClient
      .post('demande/actionOnNumber', request)
      .subscribe({
        next: (res) => {
          if (!res['hasError']) {
            this.utilities.showNotification(
              'snackbar-success',
              this.utilities.formatMsgServeur(res['status']['message']),
              'bottom',
              'center'
            );
            //this.getItems(this.searchData);
            this.getListNumbers()
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
      });
  }

  demandeDeblocage(item, val) {
    Swal.fire({
      title: 'Saisir le motif de déblocage',
      input: 'textarea',
      inputAttributes: {
        autocapitalize: 'off',
      },
      showCancelButton: true,
      confirmButtonText: 'Valider',
      cancelButtonText: 'Annuler',
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      showLoaderOnConfirm: true,
    }).then((result) => {
      if (result.isConfirmed) {
        console.log('eee', result.value);
        if (Array.isArray(item)) {
          item = item.map((param) => {
            const objet = {
              idStatus: val,
              numero: param.numero,
              motif: result.value ? result.value : '',
              portNumber: param.portNumber,
              contractId: param.contractId,
              offerName: param.offerName,
              activationDate: param.activationDate,
              statusBscs: param.statusBscs,
              isTransfert: false,
              typeNumeroId: param.typeNumeroId,
              idTypeCategory: param.idCategory
            };

            return objet;
          });
          this.demande(item, val);
        } else {
          const obj = {
            idStatus: val,
            numero: item.numero,
            motif: result.value ? result.value : '',
            portNumber: item.portNumber,
            contractId: item.contractId,
            offerName: item.offerName,
            activationDate: item.activationDate,
            statusBscs: item.statusBscs,
            isTransfert: false,
            typeNumeroId: item.typeNumeroId,
            idTypeCategory: item.idCategory
          };
          this.demande([obj], val);
        }
        //this.demande(item,val,result.value);
      }
    });
  }


  demandeMiseEnMachine(item: any, val) {
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
        console.log('eee', result.value);
        //this.demande(item,val,result.value);
        if (Array.isArray(item)) {
          item = item.map((param) => {
            const objet = {
              numero: param.numero,
              typeNumeroId: param.typeNumeroId,
              idStatus: val,
              id: param.id,
            };

            return objet;
          });
          this.validate(item, val);
        } else {
          const obj = {
            numero: item.numero,
            typeNumeroId: item.typeNumeroId,
            idStatus: val,
            id: item.id,
          };
          this.validate([obj], val);
        }
        //this.validate(item, val)
      }
    });
  }

  demandeBlocage(item, val) {
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
        console.log('eee', result.value);
        //this.demande(item,val,result.value);
        if (Array.isArray(item)) {
          item = item.map((param) => {
            const objet = {
              numero: param.numero,
              typeNumeroId: param.typeNumeroId,
              idStatus: val,
              id: param.id,
            };

            return objet;
          });
          this.validate(item, val);
        } else {
          const obj = {
            numero: item.numero,
            typeNumeroId: item.typeNumeroId,
            idStatus: val,
            id: item.id,
          };
          this.validate([obj], val);
        }
        //this.validate(item, val)
      }
    });
  }

  demandeTransfertCorb(item?: any, val?: any) {
    Swal.fire({
      title: 'Transfert de corbeille',
      input: 'textarea',
      inputAttributes: {
        autocapitalize: 'off',
      },
      showCancelButton: true,
      confirmButtonText: 'Valider',
      cancelButtonText: 'Annuler',
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      showLoaderOnConfirm: true,
    }).then((result) => {
      if (result.isConfirmed) {
        console.log('eee', result.value);
        if (Array.isArray(item)) {
          item = item.map((item) => {
            /*const objet= {
              numero: param.numero,
              typeNumeroId: param.typeNumeroId
            }*/

            const objet = {
              idStatus: val,
              numero: item.numero,
              motif: result.value ? result.value : '',
              portNumber: item.portNumber,
              contractId: item.contractId,
              offerName: item.offerName,
              activationDate: item.activationDate,
              statusBscs: item.statusBscs,
              isTransfert: true,
              typeNumeroId: item.typeNumeroId,
              idTypeCategory: item.idCategory
            };

            return objet;
          });
          //this.transfertCorbeille(item)
          this.demande(item, val);
        } else {
          /*const obj= {
            numero: item.numero,
            typeNumeroId: item.typeNumeroId
          }*/

          const obj = {
            idStatus: val,
            numero: item.numero,
            motif: result.value ? result.value : '',
            portNumber: item.portNumber,
            contractId: item.contractId,
            offerName: item.offerName,
            activationDate: item.activationDate,
            statusBscs: item.statusBscs,
            isTransfert: true,
            typeNumeroId: item.typeNumeroId,
            idTypeCategory: item.idCategory
          };
          //this.transfertCorbeille([obj])
          this.demande([obj], val);
        }
        //this.transfertCorbeille(item)
      }
    });
  }

  demandeSuppression(item?: any, val?: any) {
    Swal.fire({
      title: 'Suppression',
      input: 'textarea',
      inputAttributes: {
        autocapitalize: 'off',
      },
      showCancelButton: true,
      confirmButtonText: 'Valider',
      cancelButtonText: 'Annuler',
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      showLoaderOnConfirm: true,
    }).then((result) => {
      if (result.isConfirmed) {
        console.log('eee', result.value);
        if (Array.isArray(item)) {
          item = item.map((item) => {
            /*const objet= {
              numero: param.numero,
              typeNumeroId: param.typeNumeroId
            }*/

            const objet = {
              idStatus: val,
              numero: item.numero,
              motif: result.value ? result.value : '',
              portNumber: item.portNumber,
              contractId: item.contractId,
              offerName: item.offerName,
              activationDate: item.activationDate,
              statusBscs: item.statusBscs,
              isTransfert: false,
              typeNumeroId: item.typeNumeroId,
              idTypeCategory: item.idCategory
            };

            return objet;
          });
          //this.transfertCorbeille(item)
          this.demande(item, val);
        } else {
          const obj = {
            idStatus: val,
            numero: item.numero,
            motif: result.value ? result.value : '',
            portNumber: item.portNumber,
            contractId: item.contractId,
            offerName: item.offerName,
            activationDate: item.activationDate,
            statusBscs: item.statusBscs,
            isTransfert: false,
            typeNumeroId: item.typeNumeroId,
            idTypeCategory: item.idCategory
          };
          //this.transfertCorbeille([obj])
          this.demande([obj], val);
        }
        //this.transfertCorbeille(item)
      }
    });
  }

  action(obj, val, motif?) {
    let request = {
      user: this.user.id,
      datas: [
        {
          idStatus: val,
          numero: obj.numero,
          motif: motif ? motif : '',
        },
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

  deblocage(item, val) {
    Swal.fire({
      title: 'Saisir le motif de déblocage',
      input: 'textarea',
      inputAttributes: {
        autocapitalize: 'off',
      },
      showCancelButton: true,
      confirmButtonText: 'Valider',
      cancelButtonText: 'Annuler',
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      showLoaderOnConfirm: true,
    }).then((result) => {
      if (result.isConfirmed) {
        console.log('eee', result.value);
        this.action(item, val, result.value);
      }
    });
  }
  ngOnInit(): void {
    this.response.machine = this.machineInfoService.getMachineName();
    console.log('machine name', this.machineInfoService.getMachineName());
    this.getIpAdress();
    this.getTypeNumeros();
    //this.getItems()
  }
}
