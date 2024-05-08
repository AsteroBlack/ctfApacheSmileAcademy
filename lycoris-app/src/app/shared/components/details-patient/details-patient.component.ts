import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, TemplateRef } from '@angular/core';
import * as moment from 'moment';
import { BsModalService } from 'ngx-bootstrap/modal';
import { last, Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/service/auth.service';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import Swal from 'sweetalert2';
import * as _ from "lodash";
@Component({
  selector: 'app-details-patient',
  templateUrl: './details-patient.component.html',
  styleUrls: ['./details-patient.component.scss']
})
export class DetailsPatientComponent implements OnInit {
  @Input() patientId?: string;
  @Input() dossierHospi?:any
  @Input() detailToShow?:any
  @Output() onSelectCategorieClient: EventEmitter<any> = new EventEmitter();
  @Output() onSelectDatasAssurances: EventEmitter<any> = new EventEmitter();
  user: any = {};
  busyGet?: Subscription;
  loading: boolean = false;
  endPoint : string = 'beneficiaire/';
  itemToSave:any={}
  beneficiaireInfo: any={};
  itemsHistoPriseDeConstante: any=[];
  medecinFullName: any;
  beneficiaireInfoAssurance: any;
  itemsCategorie: any;
  itemsAssurances?: any[];
  currentAssurance: any;
  listAssurancesToSave: any=[];
  constructor(private authService: AuthService, private restClient: RestClientService, private modalService: BsModalService, private utilities: UtilitiesService) {
    this.user = this.authService.currentUserValue;
    
  }
  ngAfterViewInit(){
    console.log('dossierHospi',this.dossierHospi);
    
  }
  ngOnChanges(changes: SimpleChanges) {

    if(changes['dossierHospi'] && changes['dossierHospi'].currentValue)
    {
      this.dossierHospi = changes['dossierHospi'].currentValue
      this.itemToSave = this.dossierHospi
      this.itemToSave.medecinFullName = this.dossierHospi.userNom  && this.dossierHospi.userPrenom? (this.dossierHospi.userNom+' '+this.dossierHospi.userPrenom):this.dossierHospi.medecinTraitantNom+' '+this.dossierHospi.medecinTraitantPrenom
      console.log('data passed: ',this.itemToSave);
      if(this.dossierHospi.datasAssurance || this.dossierHospi.assurances){
        this.listAssurancesToSave=[...this.dossierHospi.datasAssurance??this.dossierHospi.assurances]
        this.listAssurancesToSave.map((lats:any)=>lats.isOld=true)
        console.log('preselected: ',this.listAssurancesToSave);
      }
      

    }
  }
  
  getBenefInfo(patientId : any) {
    if(!patientId){
      return
    }
    let request = {
      user: this.user.id,
      data: {
        id: patientId
      },
    }
    console.log('data sent to serverxxxxxxx',request);
    this.busyGet = this.restClient.post('beneficiaire/getByCriteria', request)
      .subscribe(
        (res:any) => {
          if (res && res['items']) {
            this.beneficiaireInfo = res['items'][0];
            this.itemToSave.fullName = this.beneficiaireInfo.nom+' '+this.beneficiaireInfo.prenom
          }
          else {
            this.beneficiaireInfo = {};
          }
        },
        err => {
        }
      );
  }

  getDatasCategorieClient() {

    let request = {
      user: this.user.id,
      data: {
        
      },
    }
    console.log('data sent to server',request);
    this.busyGet = this.restClient.post('adminCategorieClient/getByCriteria', request)
      .subscribe(
        (res:any) => {
          if (res && res['items']) {
            this.itemsCategorie = res['items']
            console.log('this.itemsCategorie: ',this.itemsCategorie);
            
          }
          else {
            this.itemsCategorie = [];
          }
        },
        err => {
        }
      );
  }


  getHistoPriseDeConstante() {
    
    if(!this.dossierHospi.patientId){
      return
    }
    let request = {
      user: this.user.id,
      data: {
        dossierHospiId: this.dossierHospi.id
      },
    }

    console.log('data sent to server',request);
    
    this.busyGet = this.restClient.post('hospiPriseConstante/getByCriteria', request)
      .subscribe(
        (res:any) => {
          if (res && res['items']) {
            this.itemsHistoPriseDeConstante = res['items'];
          }
          else {
            this.itemsHistoPriseDeConstante = [];
          }
        },
        err => {
        }
      );
  }

  onSelectCategorie(){
    this.onSelectCategorieClient.emit(this.itemToSave.categorieClientId)
  }

  onSelectAssaurance(){
    this.onSelectDatasAssurances.emit(this.listAssurancesToSave)
  
  }
  getItemsAssurance(){
    let request = {
      user: this.user.id,
      data: {
      },

    }
    console.log('data sent to server: ',request);
    this.busyGet = this.restClient.post('adminAssurance/getByCriteria', request)
      .subscribe(
        (res:any) => {
          if (res && res['items']) {
            this.itemsAssurances = res['items'];
          }
          else {
            this.itemsAssurances = [];
          }
        },
        err => {
        }
      );
  }

  addAssurance() {

    console.log('assurance select: ',this.currentAssurance);
    console.log('all assur: ',this.listAssurancesToSave);
    

    if (!this.currentAssurance) {
      this.utilities.showNotification("snackbar-danger", "Veuillez sélectionner une assurance !",
        "bottom",
        "center");
      return;
    }

    // Verifier si ce acte n'existe pas dans le tableau
    let isExisteAssurance = _.find(this.listAssurancesToSave, (o :any) => { return (o.id == this.itemToSave?.assuranceSelected?.id || o.id == this.currentAssurance?.id) });

    if (isExisteAssurance) {
      // Dans ce cas l'acte est deja dans le tableau
      this.utilities.showNotification("snackbar-danger", "Cette assurance a déjà été sélectionnée !",
        "bottom",
        "center");
      return;
    }


    this.listAssurancesToSave.push(this?.currentAssurance);
    this.currentAssurance = "";

    // this.getMontantPartAssurance();
  }
  
  ngOnInit() {
    this.getDatasCategorieClient() 
    this.getItemsAssurance()
  }

}
