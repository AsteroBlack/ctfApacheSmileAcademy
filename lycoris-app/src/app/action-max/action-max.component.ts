import { Component, OnInit } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/service/auth.service';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import Swal from 'sweetalert2';
import * as moment from 'moment';
import * as _ from 'lodash';
import { ModalPreviewImportComponent } from './modal-preview-import/modal-preview-import.component';

@Component({
  selector: 'app-action-max',
  templateUrl: './action-max.component.html',
  styleUrls: ['./action-max.component.scss']
})
export class ActionMaxComponent implements OnInit {

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
 searchData: any = {
   start: '',
   end: '',
 };
 etatImport: any;

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
   this.getItems(this.searchData);
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
 
 getItems(param?: any) {
   let request = {
     user: this.user.id,
     data: {
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

   this.busyGet = this.restClient
     .post('actionEnMasse/getByCriteria', request) 
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



 onPeriodeEmitted(event) {
  console.log('event', event);
  this.searchData.start = moment(event.start).format('DD/MM/YYYY HH:mm:ss' );
  this.searchData.end = moment(event.end).format('DD/MM/YYYY HH:mm:ss');
  console.log('start', this.searchData.start);
  console.log('end', this.searchData.end);
  this.getItems(this.searchData);
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

 
 ngOnInit() {
   this.resetForm();
   this.getItems(this.searchData);
   this.funEtatImport();   
   }

}
