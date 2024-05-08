import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, TemplateRef } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/service/auth.service';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { UtilitiesService } from 'src/app/core/service/utilities.service';


@Component({
  selector: 'app-find-medoc',
  templateUrl: './find-medoc.component.html',
  styleUrls: ['./find-medoc.component.scss']
})
export class FindMedocComponent implements OnInit {

 
  //Declaration de la variable d'emission des valeurs
  @Output() itemMedicamentSelected = new EventEmitter<any>();
  @Input() cancelItemSelected : any;

  modalRef?: BsModalRef;
  itemToSearch: any = {};
  itemToSave: any = {};
  listItem: Array<any> = [];
  user : any = {};
  busyGet?: Subscription;
  itemSelected : any = {};
  canDisplay : boolean = false;
  
  constructor(private authService: AuthService, private restClient: RestClientService,private modalService: BsModalService,private utilities: UtilitiesService) {
    this.user = this.authService.currentUserValue;
  }

  getItems() {

    // On affiche le tableau des patients
    this.canDisplay = true;

    let request = {
      user: this.user.id,
      data: {
        libelle: this.itemToSearch.searchText ? this.itemToSearch.searchText : null
      },
      index: 0,
      size: 10
    }

    this.busyGet = this.restClient.post('pharmacieMedicament/getByCriteria', request)
      .subscribe(
        (res:any) => {
          if (res && res['items']) {
            this.listItem = res['items'];
          }
          else {
            this.listItem = [];
          }
        },
        err => {
        }
      );
  }


  openModal(data: any, template: TemplateRef<any>) {

    let config = {backdrop: true, ignoreBackdropClick: true};

    this.itemToSave = {};
    if (data) {
      // Lorsque nous sommes en modification
      this.itemToSave = Object.assign({}, data);
    }

    this.modalRef = this.modalService.show(template,Object.assign({},config, { class: 'modal-lg modal-width-75' }));
  }

  selectedItem(item : any){
    if(item){
      this.itemSelected = {...item};
      this.itemToSearch.searchText = this.itemSelected.pharmacieMedicamentLibelle;
      
      // Brodcast de la valeur
      this.itemMedicamentSelected.emit(this.itemSelected);

      // On ferme le tableau de selection des patients
      this.canDisplay = false;
    }
  }

  patientSaved($event :any){
    if($event){
      this.selectedItem($event);

      // Après ça on ferme le modal
      this.modalRef?.hide();
    }
  }

  ngOnInit(): void {
  }


  ngOnChanges(changes: SimpleChanges) {

    if(changes['cancelItemSelected'] && changes['cancelItemSelected'].currentValue)
    {
      this.itemSelected = {};
      this.itemToSearch.searchText = null;
    }
  }
}
