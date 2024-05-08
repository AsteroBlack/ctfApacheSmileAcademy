import { Component, OnInit } from '@angular/core';
import * as moment from 'moment';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/service/auth.service';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import { ModalPreviewImportComponent } from '../modal-preview-import/modal-preview-import.component';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-blocage-automatique',
  templateUrl: './blocage-automatique.component.html',
  styleUrls: ['./blocage-automatique.component.scss']
})
export class BlocageAutomatiqueComponent implements OnInit {

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
  isActiveDate:boolean= false
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

  currentItemFile: {
    fileBase64: any;
    fileName: any;
    extension: any;
    typeDocument: string;
  };

  datasCreateDem: Array<any>= []


  constructor(
    private authService: AuthService,
    private restClient: RestClientService,
    private utilities: UtilitiesService,
    private modalService: BsModalService,
  ) {
    this.user = this.authService.currentUserValue;
   this.etatImport = JSON.parse(localStorage.getItem('EtatImport') || '{}');
   console.log(this.etatImport?.etat);
  }

  pageChanged(event: any): void {
    this.currentPage = event.page;
    this.getItemsAuto(this.searchData);
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


  getItemsAuto(param?: any){
    /*let request= {
      user: this.user.id,
      isSimpleLoading:false,
      data:{
        id: "",
        code: "",
        libelle: "",
        lienFichier: "",
        updatedAt: "",
        createdAt: param?.start ? param?.start : "",
        isDeleted: "",
        updatedBy: "",
        createdBy: "",
        identifiant: "",
        isOk: "",
        lienImport: "",
        linkDownload: "",
        putOn: "",
        putOf: "",
        linkDownloadMasse: "",
        error: "",
        isAutomatically: true,
        createdAtParam: {
          operator: '[]',
          start: param?.start ? param?.start : "",
          end: param?.end ? param?.end : "",
        }
      },
      index: this.currentPage - 1,
      size: this.itemsPerPage
    }*/

    const req= {
      user: this.user.id,
      data: {
        createdAt: param?.start ? param?.start : moment().format('DD/MM/YYYY HH:mm:ss'),
        isAutomatically: true,
        createdAtParam: {
          operator: '[]',
          start: param?.start ? param?.start : moment().format('DD/MM/YYYY HH:mm:ss'),
          end: param?.end ? param?.end : moment().format('DD/MM/YYYY HH:mm:ss'),
        }
      },
      index: this.currentPage - 1,
      size: this.itemsPerPage
    }

    this.busyGet = this.restClient
      .post('actionEnMasse/getByCriteria', req)
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
        }  );
  }



  onPeriodeEmitted(event) {
   console.log('event', event);
   this.searchData.start = moment(event.start).format('DD/MM/YYYY HH:mm:ss' );
   this.searchData.end = moment(event.end).format('DD/MM/YYYY HH:mm:ss');
   console.log('start', this.searchData.start);
   console.log('end', this.searchData.end);
   if(this.isActiveDate)this.getItemsAuto(this.searchData);
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
      this.getItemsAuto(this.searchData);
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
    request.append('identifiant','')
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
            this.getItemsAuto();
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

  BlocageMasse(item, val) {
    let request = {
      user: this.user.id,
      data:
        {
          identifiant: item.identifiant,
          idStatus: val
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
            this.getItemsAuto(this.searchData);
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
            this.getItemsAuto()
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

    this.busyGet = this.restClient.post('actionEnMasse/cronLockAllUnclockNumberService', req).subscribe(
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
            this.getItemsAuto(this.searchData);
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

  ngOnInit(): void {
    this.resetForm();
    //this.funEtatImport();
    this.getItemsAuto()
  }

}
