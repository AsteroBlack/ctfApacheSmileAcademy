import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, TemplateRef } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/service/auth.service';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import Swal from "sweetalert2";
import * as _ from "lodash";
import { Router } from '@angular/router';
@Component({
  selector: 'app-modal-duo',
  templateUrl: './modal-duo.component.html',
  styleUrls: ['./modal-duo.component.scss']
})
export class ModalDuoComponent implements OnInit {

  itemToSave : any = {};
  busyGet?: Subscription;
  busySave?: Subscription;
  user : any = {};
  loading? : boolean;
  currentData: any;
  currentUser: any;
  modalRef?: BsModalRef;
  title='Saisir le code DUO'
  codeDuo:any
  constructor( private router: Router,public bsModalRef: BsModalRef,private authService: AuthService, private restClient: RestClientService,private modalService: BsModalService,private utilities: UtilitiesService) {
    this.user = this.authService.currentUserValue;

    setTimeout(() => {
      if (this.currentData) {
        this.currentUser = Object.assign({}, this.currentData);
        console.log('currentUser',this.currentUser);
      }
    }, 400);
    
  }

  confirmSaveItem() {
    if (!this.codeDuo) {
      this.utilities.showNotification("snackbar-danger", "Veuillez saisir le code duo!",
        "bottom",
        "center");
      return;
    }
    this.onSubmit()
  }

  saveItem(item :any) {

    this.loading = true;

    let itemAEnregistrer = Object.assign({}, item);

    var request = {
      // user: this.user.id,
      data: 
        itemAEnregistrer
      
    }
    console.log('item to save: ',JSON.stringify(request));
    
    this.busyGet = this.restClient.post('user/changePassword', request)
      .subscribe(
        (res:any) => {
          console.log("resul", res);
          this.loading = false;

          if (!res['hasError']) {
            if (res['items'] && res['items'].length > 0) {
              this.utilities.showNotification("snackbar-success",
                this.utilities.formatMsgServeur(res['status']['message']),
                "bottom",
                "center");
                // this.cancelItem(true);
                this.itemToSave = {};
                this.bsModalRef.hide()

            }
          } else {
            if (res['status'] && res['status']['message']) {
              this.utilities.showNotification("snackbar-danger",
                this.utilities.formatMsgServeur(res['status']['message']),
                "bottom",
                "center");
            }
          }
        },
        err => {
          this.utilities.showNotification("snackbar-danger", this.utilities.getMessageEndPointNotAvailble(),
            "bottom",
            "center");
          this.loading = false;
        }
      );
  }

  onSubmit() {
 
    // this.authService
    //     .login(this.currentUser.login, this.currentUser.password)
    //     .subscribe(
    //       (res) => {
    //         console.log('res',res['hasError']);
            
    //         if (res && !res['hasError']) {
    //           console.log('res authxx',res);
              
    //           sessionStorage.setItem("currentUser",JSON.stringify(res['items'][0]));
              //this.authService.setCurrentUser();
              this.bsModalRef.hide();
              this.router.navigate(["/admin/dashboard/main"]);
        //     }
        //     } 
        //   ,
        //   (error) => {
        //     // this.error = error;
        //     console.log('error',error);

        // }
        // )

      }

  ngOnInit(): void {
   
  }

}
