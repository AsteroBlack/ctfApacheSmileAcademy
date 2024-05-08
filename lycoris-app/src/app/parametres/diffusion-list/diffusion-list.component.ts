import { DiffusionListService } from './diffusion-list.service';
import { Component, OnInit, TemplateRef } from '@angular/core';
import { Subscription, switchMap, tap } from 'rxjs';
import { DiffusionInfos } from './diffusion-list.model';
import Swal from 'sweetalert2';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { ParametrageGetListParametrages } from './parametrages.model';
import { UtilitiesService } from 'src/app/core/service/utilities.service';

@Component({
  selector: 'app-diffusion-list',
  templateUrl: './diffusion-list.component.html',
  styleUrls: ['./diffusion-list.component.scss']
})
export class DiffusionListComponent implements OnInit {
  modalRef: BsModalRef;

  busyGet: Subscription;
  busySave: Subscription;

  parametrages: ParametrageGetListParametrages[] = [];
  items: DiffusionInfos[] = [];

  searchTerm = "";
  currentPage: number = 1;
  itemsPerPage: number = 10;
  totalItems: number;

  emptyItemToEdit: DiffusionInfos = {
    fullname: "",
    email: "",
    tel: ""
  };
  itemToEdit: DiffusionInfos;
  editionRequestPerform = false;
  filePath= ""
  itemsTampon: Array<any>= []

  constructor(private modalService: BsModalService, private diffusionListService: DiffusionListService, private utilities: UtilitiesService) { }

  ngOnInit(): void {
    this.getInitialDatas();
  }

  getInitialDatas() {
    this.busyGet = this.diffusionListService.getParametrageList().pipe(
      tap(response => {
        this.parametrages = response
        if (this.parametrages.length > 0) {
          this.emptyItemToEdit.idParametrage = this.parametrages[0].id;
        }
      }),
      switchMap(() => this.getItemsObservable())
    ).subscribe(this.getItemsObservableSubscriptionParam())
  }

  getItemsObservable() {
    return this.diffusionListService.getList(this.searchTerm);
  }

  getItems() {
    this.busyGet = this.getItemsObservable().subscribe(this.getItemsObservableSubscriptionParam())
  }

  getItemsObservableSubscriptionParam() {
    return {
      next: response => {
        this.items = response.items.map((item): DiffusionInfos => ({
          id: item.id,
          idParametrage: item.idParametrage,
          fullname: item.nomPrenom,
          email: item.email,
          tel: item.numero
        }))
        this.itemsTampon= this.items
        this.filePath= response.filePathDoc ? response.filePathDoc : ""
        console.log("filePath", this.filePath)
      }
    }
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

  pageChanged(event: any): void {
    this.currentPage = event.page;
    this.getItems();
  }

  openModal(diffusionInfos: DiffusionInfos, template: TemplateRef<HTMLDivElement>) {
    let config = {
      backdrop: true,
      ignoreBackdropClick: true,
      id: 1,
      class: 'modal-lg',
    };

    this.itemToEdit = diffusionInfos;
    this.modalRef = this.modalService.show(template, config);
  }

  confirmSaveItem(value: DiffusionInfos) {
    Swal.fire({
      title: 'Utilisateur',
      text: this.itemToEdit?.id
        ? 'Êtes vous sûr de modifier ces informations de diffusions ?'
        : "Êtes vous sûr de créer ces informations de diffusionsn ?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non',
    }).then((result) => {
      if (result.value) {
        this.saveItem(value);
      }
    });
  }

  saveItem(value: DiffusionInfos) {
    if (this.parametrages.length > 0) {
      const datas = {
        ...value,
        idParametrage: this.parametrages[0].id
      }

      const editionSubscriptionObject = {
        next: response => {
         this.editionSuccessActions();
        },
        error: _ => {
          this.editionRequestPerformStop();
        }
      }

      this.editionRequestPerformStart();
      if (this.itemToEdit.id) {
        this.diffusionListService.update(datas).subscribe(editionSubscriptionObject)
      }
      else {
        this.diffusionListService.create(datas).subscribe(editionSubscriptionObject)
      }
    }

    else {
      this.utilities.showNotification(
        'snackbar-danger',
        "Aucun parametrage trouvé",
        'bottom',
        'center'
      )
    }

  }

  editionSuccessActions(){
    this.getItems();
    this.modalRef.hide();
    this.editionRequestPerformStop();
    this.itemToEdit = {...this.emptyItemToEdit}
  }

  editionRequestPerformStart() {
    this.editionRequestPerform = true;
  }

  editionRequestPerformStop() {
    this.editionRequestPerform = false;
  }

  confirmDelete(id: number) {
    Swal.fire({
      title: 'Diffusion Infos',
      text: "Êtes vous sûr de vouloir supprimer ces informations de diffusions ?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#ff7900',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non',
    }).then((result) => {
      if (result.value) {
        this.delete(id);
      }
    });
  }

  delete(id: number) {
    this.diffusionListService.delete(id).subscribe({
      next: response => {
        this.getItems()
      }
    })
  }

  filterItems() {
    if (!this.searchTerm || this.searchTerm === "") {
      this.items = this.itemsTampon;
      return;
    }
  
    const searchTermLower = this.searchTerm.toLowerCase();
  
    this.items = this.items.filter(item => {
      const fullName= (item.fullname || "").toLowerCase()
      const email= (item.email || "").toLowerCase()
      const tel= (item.tel || "").toLowerCase()
  
      return (
        fullName.includes(searchTermLower) ||
        email.includes(searchTermLower) ||
        tel.includes(searchTermLower)
      );
    });
  
    console.log("tab filtered", this.items);
    // return this.items
  }
}
