import { Component, OnInit, TemplateRef } from "@angular/core";
import { BsModalRef, BsModalService } from "ngx-bootstrap/modal";
import { Subscription } from "rxjs";
import { AuthService } from "src/app/core/service/auth.service";
import { RestClientService } from "src/app/core/service/rest-client.service";
import { UtilitiesService } from "src/app/core/service/utilities.service";
import Swal from "sweetalert2";
import * as moment from "moment";
import * as _ from "lodash";

@Component({
  selector: 'app-workflow-circuit',
  templateUrl: './workflow-circuit.component.html',
  styleUrls: ['./workflow-circuit.component.scss']
})
export class WorkflowCircuitComponent implements OnInit {

  listItems: Array<any> = [];
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
  endPoint : string = 'codeEntite/';
  itemsRole: any;
  itemsSpecialites: any;
  dateNais: any;
  bsValue: Date;
  ListNoParentFonctionnalites: any[];

  constructor(private authService: AuthService, private restClient: RestClientService, private modalService: BsModalService, private utilities: UtilitiesService) {
    this.user = this.authService.currentUserValue;
  }

  pageChanged(event: any): void {
    this.currentPage = event.page ;
    this.getItems();
  }

  openModal(data: any, template: TemplateRef<any>) {

    let config = {backdrop: true, ignoreBackdropClick: true};

    this.itemToSave = {};
    if (data) {
      // Lorsque nous sommes en modification
      this.itemToSave = Object.assign({}, data);
    }

    this.modalRef = this.modalService.show(template,Object.assign({},config, { class: 'modal-lg' }));
  }

  hideModal($event) {
    this.getItems();
    this.modalRef.hide();
  }

  getItems() {
    let request = {
      user: this.user.id,
      data: {
        // libelle: this.itemToSearch.libelle ? this.itemToSearch.libelle : null
      },
      index: (this.currentPage - 1),
      size: this.itemsPerPage
    }

    this.busyGet = this.restClient.post(this.endPoint+'/getByCriteria', request)
      .subscribe(
        res => {
          if (res && res['items']) {
            this.items = res['items'];
            this.totalItems = res['count'];

          }
          else {
            this.items = [];
            this.totalItems = 0;
          }
        },
        err => {
        }
      );
  }

  ngOnInit() {
    this.getItems();
  }
}
