import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, TemplateRef } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/service/auth.service';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import Swal from "sweetalert2";
import * as _ from "lodash";

@Component({
  selector: 'app-modal-update-password',
  templateUrl: './modal-update-password.component.html',
  styleUrls: ['./modal-update-password.component.scss']
})
export class ModalUpdatePasswordComponent implements OnInit {

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
  title='Modifier mot de passe'
  listAssurances: any;
  confirmnewPassword:any;
  constructor(public bsModalRef: BsModalRef,private authService: AuthService, private restClient: RestClientService,private modalService: BsModalService,private utilities: UtilitiesService) {
    this.user = this.authService.currentUserValue;
    
  }

  confirmSaveItem(item :any) {

    let objToSave = Object.assign({}, item);

    if (!item || !item.login) {
      this.utilities.showNotification("snackbar-danger", "Veuillez renseiger login!",
        "bottom",
        "center");
      return;
    }

    if (!item || !item.password) {
      this.utilities.showNotification("snackbar-danger", "Veuillez renseiger ancien mot de passe!",
        "bottom",
        "center");
      return;
    }

    if (!item || !item.newPassword) {
      this.utilities.showNotification("snackbar-danger", "Veuillez renseiger nouveau mot de passe!",
        "bottom",
        "center");
      return;
    }
    if (item.newPassword != this.confirmnewPassword) {
      this.utilities.showNotification("snackbar-danger", "Mots de passe non identiques!",
        "bottom",
        "center");
      return;
    }
    Swal.fire({
      title: "Modidifier mot de passe",
      text: "Vous êtes sur le point de modifier votre mot de passe. Voulez-vous poursuivre cette action ?",
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
    
    this.busyGet = this.restClient.post('user/changePassword', request)
      .subscribe(
        (res :any) => {
          console.log("resul", res);
          this.loading = false;

          if (!res['hasError'] )  {
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

  ngOnInit(): void {
   
  }

}
