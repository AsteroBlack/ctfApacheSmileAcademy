import { Component, OnInit } from '@angular/core';
import * as moment from 'moment';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/service/auth.service';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import { ModalPreviewImportComponent } from '../modal-preview-import/modal-preview-import.component';
import { TypeNumeroService } from 'src/app/core/service/type-numero.service';
import { TypeNumeroLabel, ResponseTypeNumero } from 'src/app/core/models/type-numero.model';
import Swal from 'sweetalert2';
import { map, tap, switchMap, catchError } from 'rxjs/operators';

@Component({
  selector: 'app-deblocage-masse',
  templateUrl: './deblocage-masse.component.html',
  styleUrls: ['./deblocage-masse.component.scss']
})
export class DeblocageMasseComponent implements OnInit {

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
  endPoint: string = 'actionUtilisateur/';
  bsValue: Date;
  click: boolean = false;
  isActiveDate: boolean= false
  searchData: any = {
    start: '',
    end: '',
  };
  intervalValue: any;
  fileName: string;
  fileDisplay: any;
  etatImport: any;
  filePathDoc: string;
  filePath= ""
  idStatu= null

  currentItemFile: {
    fileBase64: any;
    fileName: any;
    extension: any;
    typeDocument: string;
  };

  datasCreateDem: Array<any>= []
  endPointIdStatu: string= 'status/'

  private typeNumeros: ResponseTypeNumero[]

  constructor(
    private authService: AuthService,
    private restClient: RestClientService,
    private utilities: UtilitiesService,
    private modalService: BsModalService,
    private typeNumeroService: TypeNumeroService
  ) {
    this.user = this.authService.currentUserValue;
   this.etatImport = JSON.parse(localStorage.getItem('EtatImport') || '{}');
   console.log(this.etatImport?.etat);
  }


  pageChanged(event: any): void {
    this.currentPage = event.page;
    /*if(this.isActiveDate){
      this.getItems(this.searchData);
    }else this.getItems()*/
    this.getItems(this.searchData)
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
      data: {
        createdAt: this.searchData?.start ? this.searchData?.start : moment().format('DD/MM/YYYY'),
        createdAtParam: {
          operator: '[]',
          start: this.searchData?.start ? this.searchData?.start : moment().format('DD/MM/YYYY'),
          end: this.searchData?.end ? this.searchData?.end : moment().format('DD/MM/YYYY'),
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

  getTypeNumeros() {
    if (this.typeNumeros === undefined) {
      this.busyGet= this.typeNumeroService.getList().subscribe(
        {
          next: (res) =>{
            if(res && res.length >=2){
              this.typeNumeros= res
              console.log("typNum", this.typeNumeros)
            }else{
              this.typeNumeros= undefined
            }
          },
          error: (err) => console.log(err),
          complete: () => console.log("complete")
        }
      );
    }
  }

  getTypeNumero(label: TypeNumeroLabel) {
    return this.typeNumeros.find(typeNumero => typeNumero.libelle === label);
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

    return this.restClient.post(this.endPointIdStatu + 'getByCriteria', req)
  }


  getItems(param?: any) {
    let request = {
      user: this.user.id,
      data: {
        createdAt: param?.start ? param?.start : moment().format('DD/MM/YYYY HH:mm:ss'),
        isAutomatically: false,
        idStatus: null,
        createdAtParam: {
          operator: '[]',
          start: param?.start ? param?.start : moment().format('DD/MM/YYYY HH:mm:ss'),
          end: param?.end ? param?.end : moment().format('DD/MM/YYYY HH:mm:ss'),
        }

      },
      index: this.currentPage - 1,
      size: this.itemsPerPage,
    };


    this.busyGet= this.getIdStatus()
      .pipe(
        tap((res) => {
          if (!res['hasError'] && res['items']) {
            const statuDebloque= res['items'].filter((item: any) => item.code == 'DEBLOQUE')
            request.data.idStatus= statuDebloque[0]['id']
            this.idStatu= statuDebloque[0]['id']
            console.log("idStatus", res)
            console.log("debloque", statuDebloque)
          } else request.data.idStatus= ""
        }),
        switchMap(() => this.restClient.post('actionEnMasse/getByCriteria', request))
      )
      .subscribe(
        {
          next: (res) => {
            if (res && res['items']) {
              this.items = res['items'];
              this.totalItems = res['count'];
              this.filePath= res['filePathDoc'] ? res['filePathDoc'] : ""
            } else {
              this.items = [];
              this.totalItems = 0;
            }
          },
          error: (err) => console.info(err),
          complete: () => console.info('complete'),
        } 
      )



    /*this.busyGet = this.restClient
      .post('actionEnMasse/getByCriteria', request)
      .subscribe(
        {
          next: (res) => {
            if (res && res['items']) {
              this.items = res['items'];
              this.totalItems = res['count'];
              this.filePath= res['filePathDoc'] ? res['filePathDoc'] : ""
            } else {
              this.items = [];
              this.totalItems = 0;
            }
          },
          error: (err) => console.info(err),
          complete: () => console.info('complete'),
        }  );*/
  }


  refreshNumbers(){
    const req= {
      user: this.user.id
    }

    this.busyGet = this.restClient.post(this.endPoint + 'cronRefreshAllNumberForActionenMasse', req).subscribe(
      (res) => {
        if (res && !res['hasError']) {
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



  deleteActionMasse(obj?: any){
    const req= {
      user: this.user.id,
      datas: obj
    }

    this.busyGet = this.restClient.post('actionEnMasse/delete', req).subscribe(
      (res) => {
        if (res && !res['hasError']) {
          this.utilities.showNotification(
              'snackbar-success',
              this.utilities.formatMsgServeur(res['status']['message']),
              'bottom',
              'center'
            );
            this.getItems()
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




  reblockNumbers(){
    const req= {
      user: this.user.id
    }

    this.busyGet = this.restClient.post('actionEnMasse/setjobLockUnlockNumber', req).subscribe(
      (res) => {
        if (res && !res['hasError']) {
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



  importAuto(){
    const req= {
      user: this.user.id
    }

    this.busyGet = this.restClient.post('actionEnMasse/importCsvToDBJob', req).subscribe(
      (res) => {
        if (res && !res['hasError']) {
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


  demandeSuppression(item?: any, val?: any){
    Swal.fire({
      title: 'Suppression',
      text: 'Voulez vous poursuivre ?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'OUI',
      cancelButtonText: 'NON',
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      showLoaderOnConfirm: true,
    }).then((result) => {
      if (result.isConfirmed) {
        console.log("eee",result.value);
        if(Array.isArray(item)){
          item= item.map(item =>{
            
            const objet= {
              id: item.id
            }
            
            return objet
          })
          this.deleteActionMasse(item)
        }else{

          const obj= {
            id: item.id
          }
          this.deleteActionMasse([obj])
        }
      }
    })
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
    hasAuthorithy= data.some(item => item.isOk === false)
    console.log("isOk", data)

    return hasAuthorithy
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
      this.getItems(this.searchData);
      this.etatImport = JSON.parse(localStorage.getItem('EtatImport') || '{}');
      this.funEtatImport();
    });
  }

  uploadFile(event: any) {
    const reader = new FileReader();
    if (event.target.files && event.target.files.length) {
      const file = event.target.files[0];
      const fileName = file.name.split('.')[0];
      const Tabextension = file.name.split('.');
      const extension = Tabextension[Tabextension.length - 1];
      this.fileName = fileName + '.' + extension;
      console.log(file);
      reader.onload = (readerEvent) => {
        const data = (readerEvent.target as any).result;
        this.fileDisplay = data;
        const fileBase64 = data.split(',')[1];
        this.currentItemFile = {
          fileBase64: fileBase64,
          fileName: fileName,
          extension: extension,
          typeDocument: 'custom type',
        };
        this.setFileData(file);
      };
    }

    reader.readAsDataURL(event.target.files[0]);
  }

  setFileData(data?) {
    console.log('this.currentItemFile', data);
    this.loading = true;
    let request = new FormData();
    request.append('file', data, data.name);
    request.append('user', this.user.id);
    request.append('date', moment().format('DD/MM/YYYY'));
    request.append('identifiant',''),
    request.append('idStatus', '2')
    let methode = this.currentItemFile.extension == 'xlsx' ? 'uploadOneFile' : 'uploadFileCsv'
    console.log("methode", methode);

    this.busyGet =this.restClient
      .post('actionEnMasse/' + methode, request)
      .subscribe({
        next: (res) => {
          this.loading = false;
          if (!res['hasError']) {
            this.utilities.showNotification(
              'snackbar-success',
              this.utilities.formatMsgServeur(res['status']['message']),
              'bottom',
              'center'
            );
            //localStorage.setItem("EtatImport",JSON.stringify({etat: '1', date: moment().format('DD/MM/YYYY')}));
            this.currentItemFile = {
              fileBase64: '',
              fileName: '',
              extension: '',
              typeDocument: '',
            };
            this.getItems();
          } else {
            if (res['status'] && res['status']['message']) {
              this.loading = false;
              this.utilities.showNotification(
                'snackbar-danger',
                this.utilities.formatMsgServeur(res['status']['message']),
                'bottom',
                'center'
              );
            }
          }
        },
        error: (err) => {
          this.loading = false;
          this.utilities.showNotification(
            'snackbar-danger',
            this.utilities.getMessageEndPointNotAvailble(),
            'bottom',
            'center'
          );
          this.loading = false;
        },
        complete: () => console.info('complete'),
      });
  }

  deBlocageMasse(item, val) {
    let request = {
      user: this.user.id,
      data:
        {
          identifiant: item.identifiant,
          idStatus: this.idStatu,
          typeNumeroId: this.getTypeNumero(TypeNumeroLabel.CORAIL).id
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
            this.getItems(this.searchData);
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

//   telecharger(lien?: any){
//     if (lien) {
//       window.open(lien); // Ouvre le lien de téléchargement dans une nouvelle fenêtre
//       this.utilities.showNotification(
//         'snackbar-success',
//         this.utilities.formatMsgServeur('Téléchargement en cours...'),
//         'bottom',
//         'center'
//       );
//     } else {
//       this.utilities.showNotification(
//         'snackbar-danger',
//         this.utilities.formatMsgServeur('échec du téléchargement'),
//         'bottom',
//         'center'
//       );
//     }
// }

  ngOnInit(): void {
    this.resetForm();
    this.getItems();
    //this.funEtatImport();
    this.getTypeNumeros()
  }

}
