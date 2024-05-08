import { Component, OnInit, TemplateRef } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/service/auth.service';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import Swal from "sweetalert2";
import * as moment from 'moment';
import * as _ from 'lodash';
@Component({
  selector: 'app-roles',
  templateUrl: './roles.component.html',
  styleUrls: ['./roles.component.scss']
})
export class RolesComponent implements OnInit {
  enable=true
  maxDate = new Date();
  ModalConfig = {
    backdrop: true,
    ignoreBackdropClick: false
  };
  itemToSave:any = {};
  itemToSearch:any = {};
  result: any;
  page = 4;
  config: any;
  collection = { count: 60, data: [] };
  public maxSize: number = 7;
  public directionLinks: boolean = true;
  public autoHide: boolean = false;
  public responsive: boolean = true;
  labels: any = {
    previousLabel: '<--',
    nextLabel: '-->',
    screenReaderPaginationLabel: 'Pagination',
    screenReaderPageLabel: 'page',
    screenReaderCurrentLabel: `You're on page`
  };
  ListRoles: any[];
  ListStatus: any;
  ListProfile: any;
  private imageSelected = false;
  dataNom: any;
  selectedRow: any;
  busyGet:Subscription
  modalRef: any;
  disabledMode: boolean=false;
  ListDomaines: any[];
  ListFonctionnalites: any;
  isAllParentChecked=false
  listItems: Array<any> = [];
  items: Array<any> = [];
  user: any = {};
  currentPage: number = 1;
  itemsPerPage: number = 20;
  totalItems: number;
  busySave: Subscription;
  loading: boolean = false;
  itemsRole: any;
  bsValue: Date;
  filePath= ""

  constructor(private authService: AuthService, private restClient: RestClientService, private modalService: BsModalService, private utilities: UtilitiesService) {
    this.user = this.authService.currentUserValue;
  }

  ngOnInit() {
    this.getDataFonctionnalites()
    setTimeout(() => {
      this.getItems()
    }, 200);

  }
  setCheckedFull(event, item?) {
    if (this.ListFonctionnalites && this.ListFonctionnalites.length){
    this.ListFonctionnalites.map((fonct: any) => {
        fonct.isChecked = event.target.checked;
        fonct.isOpen = event.target.checked;
        if (fonct.datasChildren && fonct.datasChildren.length){
          fonct.datasChildren.map(
            child => {
              child.isChecked = event.target.checked;
              child.isOpen = event.target.checked;
            }
          );
        }

        }
    );
    }
  }
  checkIfAllFuncSelected(){
    let parentFonctionnalites = this.ListFonctionnalites.filter(
      pf=> !pf.idParent
    )
    console.log('all parent func',parentFonctionnalites);
    let unChekedParents = parentFonctionnalites.filter(
      pF=> !pF.isChecked
    )

    this.isAllParentChecked = !unChekedParents.length

  }
  setCheckedChildItem(event, item?) {
    let totalChecked = 0
    if (item.datasChildren && item.datasChildren.length){
      item.datasChildren.map(
        child => {
          if(child.isChecked){
            totalChecked ++
            console.log('total',totalChecked);

          }
        }
      );

        item.isChecked = item.datasChildren.length == totalChecked

    }
    this.checkIfAllFuncSelected()

  }
  displayDetails(item) {
    console.log('item to view', item);
    item.isOpen = !item.isOpen;
  }
  setCheckedItem(event, item?) {
    console.log('event',event);
    console.log('item',item);


    if (item.datasChildren && item.datasChildren.length){
      item.datasChildren.map(
        child => {
          child.isChecked = event.target.checked;
        }
      );
    }
    this.checkIfAllFuncSelected()


  }
  cancelSave(){
    this.itemToSave = {}
    this.disabledMode = false
    this.isAllParentChecked = false
  }
  cancelSearch(){
    this.itemToSearch = {}
    this.getItems()
  }
  pageChanged(event){
    this.config.currentPage = event;
    this.getItems()
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

  getItems(mode?){
    if(mode)
        this.config.currentPage = 1
    let request = {
      user: this.user.id,
      isSimpleLoading:false,
      // index: (this.currentPage - 1),
      // size: this.itemsPerPage,
      data:{
        libelle: this.itemToSearch.libelle

      }
    }
    this.busyGet =this.restClient.post('profil/getByCriteria',request).subscribe(
      (res:any)=>{
        console.log('return of getData',res);
        if(res && !res.hasError){
          if(res.status.code ==903){
            this.result = []
          }
          else{
            this.result = res.items
            this.selectedRow = this.result[0];
            this.onSetMatchedFuncs(this.selectedRow)
            this.filePath= res['filePathDoc'] ? res['filePathDoc'] : ""
          }
          this.totalItems = res.count

        }
        else{
          this.result = []
          this.utilities.showNotification("snackbar-danger",
                this.utilities.formatMsgServeur(res.status.message),
                "bottom",
                "center");

        }
      },
      (error:any)=>{
        this.result = []
        this.utilities.showNotification("snackbar-danger",
                this.utilities.formatMsgServeur(error.message),
                "bottom",
                "center");
      }
    )


  }

  getDataFonctionnalites(){
    let request = {
      user: this.user.id,
      isSimpleLoading:false,
      hierarchyFormat:true,
      data:{

      }
    }
    this.busyGet =this.restClient.post('fonctionnalite/getByCriteria',request).subscribe(
      (res:any)=>{
        console.log('return of getData',res);
        if(res && !res.hasError){
          if(res.status?.code ==903){
            this.ListFonctionnalites = []
          }
          else{
            this.ListFonctionnalites = res.items
          }
          this.totalItems = res.count
          console.log('all funct return',this.ListFonctionnalites);
        }
        else{
          this.ListFonctionnalites = []
          this.utilities.showNotification("snackbar-danger",
                this.utilities.formatMsgServeur(res.status.message),
                "bottom",
                "center");
        }
      },
      (error:any)=>{
        this.ListFonctionnalites = []
        this.utilities.showNotification("snackbar-danger",
              'Connexion momentanément interrompue',
                "bottom",
                "center");
      }
    )


  }

  onConfirmSave(){
    let data =this.itemToSave
    if(!data.libelle){
      this.utilities.showNotification("snackbar-danger",
          'Veuillez renseigner libellé',
          "bottom",
          "center");
      return
    }
    console.log('fonct content: ', this.ListFonctionnalites);
    const fonctionnalites = []
    let parentFonctionnalites = []

        this.ListFonctionnalites.map(
        fonc => {
          if (fonc.datasChildren && fonc.datasChildren.length){
            fonc.datasChildren.forEach(item => {
              if (item.isChecked) {
                fonctionnalites.push(item)
              }
            });
          }

        }
    )
    console.log('fonctionnalites without parents: ', fonctionnalites);

    console.log('fonctionnalites without parents: ', JSON.stringify(fonctionnalites));
    parentFonctionnalites = this.ListFonctionnalites.filter(
     pf=> !pf.idParent
   )
   //console.log('parent fonctionnlites list',parentFonctionnalites );

   parentFonctionnalites.map(
     pf=>{
       if(pf.isChecked){
         if(!fonctionnalites.includes(pf)){
           fonctionnalites.push(pf)
         }

       }
       else if(this.hasChildrenIncluded(pf.id,fonctionnalites)){
         fonctionnalites.push(pf)
       }
     }
   )

     if (!fonctionnalites.length){
      //  this.toastr.error('Veuillez choisir au moins une fonctionnalité svp!', 'Erreur');
       return;
     }

    Swal.fire({

      title: data.id ? "Mise à jour" : "Enregistrement",

      text: data.id?  "Vous êtes sur le point de mettre a jour un profil. Voulez-vous poursuivre cette action ?" : "Vous êtes sur le point d'enregistrer un profil. Voulez-vous poursuivre cette action ?",
      icon: 'warning',
      showCancelButton: true,
     confirmButtonColor: "#ff7900",
      cancelButtonColor: "#d33",
      confirmButtonText: 'Oui',
      cancelButtonText:'Non'
    }).then((result) => {
      if (result.value) {
        this.saveItem(data,fonctionnalites)
      }
    })

  }



  saveItem(data,fonctionnalites){
    fonctionnalites.map(
      f=>{
        // delete f.code,delete f.name,delete f.parentId,delete f.isChecked,delete f.isOpen,delete f.datasChildren,
        //  delete f.updatedAt,delete f.updatedBy, delete f.createdAt,delete f.createdBy,delete f.isDeleted
      }
    )
    let action = data.id?'update':'create';
    let request = {
      user: this.user.id,
      isSimpleLoading:false,
      datas:[
        {
          id: data.id,
          libelle: data.libelle,
          datasFonctionalites: fonctionnalites
        }
      ]
    }
    console.log('saveItem',request);
    console.log('saveItem',JSON.stringify(request));
    this.busyGet = this.restClient.post('profil/'+action,request).subscribe(
      (res:any)=>{
        if(res && !res.hasError){
          this.utilities.showNotification("snackbar-success",
          this.utilities.formatMsgServeur(res.status.message),
          "bottom",
          "center");
          this.cancelSave()
          this.cancelSearch()
          // this.getItems()
          // this.getDataFonctionnalites()
        }
        else{
          this.utilities.showNotification("snackbar-danger",
          this.utilities.formatMsgServeur(res.status.message),
          "bottom",
          "center");
        }
      },
      (error:any)=>{
        this.utilities.showNotification("snackbar-success",
        'Connexion momentanément interrompue',
          "bottom",
          "center");
      }
    )
  }

  selectRow(data){
    this.selectedRow = data

  }
  onSetMatchedFuncs(data?,bool?:boolean) {
    this.cancelSave()
    console.log('bool',bool);
    this.uncheckFull();

    if(data){
      this.disabledMode=bool?bool:false
      this.itemToSave =data? {...data}:{};

    console.log('all fonc: ', this.ListFonctionnalites);
    let foncTarget = {... data}.datasFonctionalites;
    // this.listFonctionnalites = itemToModified ? {... itemToModified}.datasFonctionnalite : this.listFonctionnalites;
console.log("foncTarget",foncTarget);

    if(foncTarget && foncTarget.length){
      console.log('to be modified fonctionnalites target',foncTarget);

      this.ListFonctionnalites.map(
        lf=>{
          //lf.isChecked = _.find(foncTarget, {'fonctionnaliteCode': lf.code});
          let totalChecked = 0
          if (lf.datasChildren && lf.datasChildren.length){
            lf.datasChildren.map(
              lfchild=>{
                lfchild.isChecked = _.find(foncTarget, {'code': lfchild.code});
                // lfchild.isChecked=true
                if(lfchild.isChecked){
                  totalChecked ++
                }
                if(totalChecked>0){
                  lf.isOpen = true;
                }

                lf.isChecked = lf.datasChildren.length== totalChecked

              }
            )
          }
          else{
            console.log("code",lf.code)
            lf.isChecked = _.find(foncTarget, {'code': lf.code});
          }

        }
      )
    }
    }
    this.checkIfAllFuncSelected()

  }

  onConfirmDelete(data){

    Swal.fire({
      title: "Suppression",
      text:"Vous êtes sur le point de supprimer un profil. Voulez-vous poursuivre cette action ?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Oui',
      cancelButtonText:'Non'
    }).then((result) => {
      if (result.value) {
        this.deleteItem(data)
      }
    })

  }


  deleteItem(data){

    let action = 'delete';
    let request = {
      user: this.user.id,
      isSimpleLoading:false,
      datas:[
        {
          id:data.id
        }
      ]
    }
    console.log('deleteItem',JSON.stringify(request));
    this.busyGet = this.restClient.post('profil/'+action,request).subscribe(
      (res:any)=>{
        console.log('return of deleteItem',res);

        if(res && !res.hasError){
          this.cancelSave()
          this.cancelSearch()
          this.getItems()
        }
        else{
          this.utilities.showNotification("snackbar-danger",
          res.status.message,
          "bottom",
          "center");

        }
      },
      (error:any)=>{
        // this.toastr.error('Connexion momentanément interrompue', 'Erreur');
      }
    )
  }

  hasChildrenIncluded(id,fonctionnalites){

    return _.find(fonctionnalites, ['parentId', id]);

  }

  uncheckFull() {
    if (this.ListFonctionnalites && this.ListFonctionnalites.length){
      this.ListFonctionnalites.map((fonct: any) => {
          fonct.isChecked = false;
          fonct.isOpen = false;
          if(fonct.datasChildren && fonct.datasChildren.length){
            fonct.datasChildren.map(
              child => {
                child.isChecked = false;
                child.isOpen = false;
              }
            );
          }

        }
      );
    }

  }

}
