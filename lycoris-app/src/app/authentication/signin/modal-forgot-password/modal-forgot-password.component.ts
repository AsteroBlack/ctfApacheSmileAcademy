import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, TemplateRef } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/service/auth.service';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import Swal from "sweetalert2";
import * as _ from "lodash";

@Component({
  selector: 'app-modal-forgot-password',
  templateUrl: './modal-forgot-password.component.html',
  styleUrls: ['./modal-forgot-password.component.scss']
})
export class ModalForgotPasswordComponent implements OnInit {

  itemToSave : any = {};
  busyGet?: Subscription;
  busySave?: Subscription;
  listTypeAntecedent : Array<any> = [];
  listAntecedentToSave : any= [];
  user : any = {};
  loading? : boolean;
  currentData: any;
  currentDossier: any;
  dossierId: any;
  modalRef?: BsModalRef;
  title='reinitialiser mot de passe'
  listAssurances: any;
  constructor(public bsModalRef: BsModalRef,private authService: AuthService, private restClient: RestClientService,private modalService: BsModalService,private utilities: UtilitiesService) {
    this.user = this.authService.currentUserValue;
    
  }
  confirmSaveItem(item :any) {

    let objToSave = Object.assign({}, item);

    if (!item || !item.email) {
      this.utilities.showNotification("snackbar-danger", "Veuillez renseiger email!",
        "bottom",
        "center");
      return;
    }

    

    Swal.fire({
      title: "Reinitialiser mot de passe",
      text: "Vous êtes sur le point de reinitialiser votre compte. Voulez-vous poursuivre cette action ?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#0665aa",
      cancelButtonColor: "#aaa4a4",
      confirmButtonText: "Oui",
      cancelButtonText: 'Non',
    }).then((result) => {
      if (result.value) {
        this.saveItem(objToSave);
      }
    });
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
    
    this.busyGet = this.restClient.post('user/resetAccount', request)
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
                this.authService.logout()
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

  ngOnInit(): void {
   
  }

}
