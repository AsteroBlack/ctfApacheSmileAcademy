import { Component, EventEmitter, Input, OnInit, Output, TemplateRef } from "@angular/core";
import { BsModalRef, BsModalService } from "ngx-bootstrap/modal";
import { Subscription } from "rxjs";
import { AuthService } from "src/app/core/service/auth.service";
import { RestClientService } from "src/app/core/service/rest-client.service";
import { UtilitiesService } from "src/app/core/service/utilities.service";
import Swal from "sweetalert2";
import * as moment from "moment";
import * as _ from "lodash";

@Component({
  selector: 'app-form-circuit-validation',
  templateUrl: './form-circuit-validation.component.html',
  styleUrls: ['./form-circuit-validation.component.scss']
})
export class FormCircuitValidationComponent implements OnInit {

  itemToSave : any= {};
  user : any = {};
  busyGet: Subscription;
  ListeUtilisateurs : Array<any> = [];
  ListeValidateurs : Array<any> = [];
  loading : boolean;
  endPoint = 'workflowValidation/'
  isUpdate : boolean = false;

  @Output() closeForm = new EventEmitter<any>();
  @Input() entite : any;


  constructor(private authService: AuthService, private restClient: RestClientService, private modalService: BsModalService, private utilities: UtilitiesService) {
    this.user = this.authService.currentUserValue;
  }


  formClose() {
    this.closeForm.emit("'fermer'");
  }


  getItemUtilisateur() {
    let request = {
      user: this.user.id,
      data: {
      }
    }

    this.busyGet = this.restClient.post('user/getByCriteria', request)
      .subscribe(
        res => {
          if (res && res['items']) {
            this.ListeUtilisateurs = _.orderBy(res['items'], ['nom'], ['asc']); 

            this.ListeUtilisateurs.forEach(user => {
              user.identite = user.nom?.toUpperCase() +' '+user.prenom?.toUpperCase()
            });

          }
          else {
            this.ListeUtilisateurs = [];
          }
        },
        err => {
        }
      );
  }


  getWorkFlowValidation() {

    let request = {
      user: this.user.id,
      data: {
        codeEntite : this.entite?.code
      }
    }

    this.busyGet = this.restClient.post('workflowValidation/getByCriteria', request)
      .subscribe(
        res => {
          console.log(" liste work",res);
          
          if (res && res['items']) {
            this.ListeValidateurs = _.orderBy(res['items'], ['nom'], ['asc']); 
            this.isUpdate = true;
            this.ListeValidateurs.forEach(user => {
              user.userIdentite = user.userNom?.toUpperCase() +' '+user.userPrenom?.toUpperCase()
            });

          }
          else {
            this.ListeValidateurs = [];
          }
        },
        err => {
        }
      );
  }


  addUser() {

    console.log(this.itemToSave);
    
    let item = {...this.itemToSave};

    if(!this.itemToSave?.userSelected) {

      this.utilities.showNotification(
        "snackbar-danger",
        "Veuillez sélectionner un utilisateur !",
        "bottom",
        "center"
      );
      return;
    }

    // Verifier si l'utilisateur n'a pas deja ajouté
    let isExist = _.find(this.ListeValidateurs, (o) => { return o.idValidateur == this.itemToSave?.userSelected?.id; });
    if(isExist) {
      this.utilities.showNotification(
        "snackbar-danger",
        "Cet utilisateur existe déjà dans la liste !",
        "bottom",
        "center"
      );
      return;
    }

    console.log(this.itemToSave);

    let userItem = {
        codeEntite : this.entite?.code,
        idValidateur : item?.userSelected?.id,
        fonctionValidateur : item?.fonctionValidateur,
        userIdentite :(item?.userSelected?.nom + " "+item?.userSelected?.prenom)?.toUpperCase() 
    }

    this.ListeValidateurs.push(userItem);

    this.itemToSave = {};

  }


  deleteItemValidateur(indice) {
    Swal.fire({
      title: "Suppression",
      text: "Confirmez-vous l'action ?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#0665aa",
      cancelButtonColor: "#aaa4a4",
      confirmButtonText: "Oui",
      cancelButtonText: 'Non',
    }).then((result) => {
      if (result.value) {
        this.ListeValidateurs.splice(indice, 1);
      }
    });
  }

  mouvementItem(item,indice,isUp) {

     console.log(" item ",item);
     
     if(isUp) {
       // Il s'agit de le mettre en +1
       // On recupere l'item en n+1
       let itemAremplacer = {...this.ListeValidateurs[indice - 1]};
       let itemActuel = {...this.ListeValidateurs[indice]};

       this.ListeValidateurs[indice - 1] = itemActuel;
       this.ListeValidateurs[indice] = itemAremplacer;


     }else {
       // On doit le mettre en -1
       let itemAremplacer = {...this.ListeValidateurs[indice + 1]};
       let itemActuel = {...this.ListeValidateurs[indice]};

       this.ListeValidateurs[indice + 1] = itemActuel;
       this.ListeValidateurs[indice] = itemAremplacer;
     }
  }

 
  confirmSaveItem(item) {

    let objToSave = Object.assign({}, item);

   // On verifie si il y a au moins un circuit
   if(!this.ListeValidateurs || this.ListeValidateurs.length == 0) {
    this.utilities.showNotification(
      "snackbar-danger",
      "Aucun circuit n'est defini !",
      "bottom",
      "center"
    );
    return;
   }

   // On format la liste pr la sauvegarder
   let indice = 0;
   this.ListeValidateurs.forEach(valideur => {
      indice = indice + 1;
      valideur.ordre = indice;
   });

    Swal.fire({
      title: "Circuit de validation",
      text: objToSave?.id ? "Vous êtes sur le point de modifier un circuit. Voulez-vous poursuivre cette action ?" : "Vous êtes sur le point d'enregistrer un circuit. Voulez-vous poursuivre cette action ?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#0665aa",
      cancelButtonColor: "#aaa4a4",
      confirmButtonText: "Oui",
      cancelButtonText: 'Non',
    }).then((result) => {
      if (result.value) {

        if(!this.isUpdate) {
          this.saveItem();
        } else {
          this.updateItem();
        }
       
      }
    });
  }

  saveItem() {

    this.loading = true;

    var request = {
      user: this.user.id,
      datas: this.ListeValidateurs
    }

    this.busyGet = this.restClient.post(this.endPoint+'/create', request)
      .subscribe(
        res => {
          console.log("resul", res);
          this.loading = false;

          if (!res['hasError']) {
            if (res['items'] && res['items'].length > 0) {

              this.utilities.showNotification("snackbar-success",
                this.utilities.formatMsgServeur(res['status']['message']),
                "bottom",
                "center");
              
              this.formClose();

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


  updateItem() {

    this.loading = true;

    var request = {
      user: this.user.id,
      data: {
        "codeEntite": this.entite?.code,
        "datasValidateur": this.ListeValidateurs
      }
    }

    this.busyGet = this.restClient.post(this.endPoint+''+ (this.isUpdate ? 'modifierWorkflow' : 'create'), request)
      .subscribe(
        res => {
          console.log("resul", res);
          this.loading = false;

          if (!res['hasError']) {
            if (res['items'] && res['items'].length > 0) {

              this.utilities.showNotification("snackbar-success",
                this.utilities.formatMsgServeur(res['status']['message']),
                "bottom",
                "center");
              
              this.formClose(); 

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
    console.log(" entite ",this.entite);
    this.getItemUtilisateur();
    this.getWorkFlowValidation();
  }

}
