import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/service/auth.service';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import * as moment from 'moment';

@Component({
  selector: 'app-modal-preview-import',
  templateUrl: './modal-preview-import.component.html',
  styleUrls: ['./modal-preview-import.component.scss'],
})
export class ModalPreviewImportComponent implements OnInit {
  itemToSave: any = {};
  user: any = {};
  title = "Prévisualiser l'import";
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
  fileName: string;
  fileDisplay: any;
  currentItemFile: {
    fileBase64: any;
    fileName: any;
    extension: any;
    typeDocument: string;
  };
  click = false;
  
  constructor(
    public bsModalRef: BsModalRef,
    private authService: AuthService,
    private restClient: RestClientService,
    private utilities: UtilitiesService
  ) {
    this.user = this.authService.currentUserValue;
  }
  pageChanged(event: any): void {
    this.currentPage = event.page;
    this.getItems();
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
    let methode = this.currentItemFile.extension == 'xlsx' ? 'uploadFileExcel' : 'uploadFileCsv'
    console.log("methode", methode);
    
    this.busyGet =this.restClient
      .post(this.endPoint + methode, request)
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
            localStorage.setItem("EtatImport",JSON.stringify({etat: '1', date: moment().format('DD/MM/YYYY')}));
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

  getItems() {
    let request = {
      user: this.user.id,
      data: {},
      index: this.currentPage - 1,
      size: this.itemsPerPage,
    };

    this.busyGet = this.restClient
      .post(this.endPoint + 'fromExcelFile', request)
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
  ngOnInit() {
    this.getItems();
  }
}
