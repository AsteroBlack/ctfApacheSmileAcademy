import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { MaterialModule } from "./material.module";
import { FeatherIconsModule } from "./components/feather-icons/feather-icons.module";
import { NgxMaskModule } from "ngx-mask";
import { NgBusyModule } from "ng-busy";
import { PaginationModule } from "ngx-bootstrap/pagination";
import { MoneyFormatPipe } from "../core/pipes/money-format.pipe";

// import { PaginationModule } from 'ngx-bootstrap';
import { BsDatepickerModule } from "ngx-bootstrap/datepicker";
import { MainListeAccueilByEtapeComponent } from "./components/main-liste-accueil-by-etape/main-liste-accueil-by-etape.component";
import { AddDocumentComponent } from "./components/add-document/add-document.component";
import { CKEditorModule } from "ng2-ckeditor";
import { MatTabsModule } from "@angular/material/tabs";
import { FindMedocComponent } from "./components/find-medoc/find-medoc.component";
import { UiSwitchModule } from 'ngx-ui-switch';
import { NgSelectModule } from "@ng-select/ng-select";
import { HistoriqueCircuitTraitementComponent } from "./components/historique-circuit-traitement/historique-circuit-traitement.component";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button";
import { DateRangeSelectorDirective } from "./date-range-selector/date-range-selector.directive";

@NgModule({
  declarations: [
    MoneyFormatPipe,
    MainListeAccueilByEtapeComponent, 
    AddDocumentComponent,
    FindMedocComponent,
    HistoriqueCircuitTraitementComponent,
    DateRangeSelectorDirective
  ],
  imports: [
    CommonModule,
    CKEditorModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    NgbModule,
    NgxMaskModule.forRoot(),
    NgBusyModule,
    PaginationModule.forRoot(),
    BsDatepickerModule.forRoot(),
    MatTabsModule,
    NgSelectModule,
    MatIconModule,
    MatButtonModule,
    UiSwitchModule.forRoot({
      size: 'small',
      color: 'green',
      switchColor: 'white',
      defaultBgColor: 'red',
      defaultBoColor : '#476EFF',
      checkedLabel: 'Actif',
      uncheckedLabel: 'Inactif',
      checkedTextColor:'white',
      uncheckedTextColor:'white'
    })
  ],
  exports: [
    CommonModule,
    CKEditorModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    MaterialModule,
    FeatherIconsModule,
    NgBusyModule,
    PaginationModule,
    MatTabsModule,
    MatIconModule,
    MatButtonModule,
    // PaginationModule.forRoot(),
    NgxMaskModule,
    MoneyFormatPipe,
    BsDatepickerModule,
    MainListeAccueilByEtapeComponent,
    AddDocumentComponent,
    FindMedocComponent,
    UiSwitchModule,
    NgSelectModule,
    NgbModule,
    HistoriqueCircuitTraitementComponent,
    DateRangeSelectorDirective
  ],
})
export class SharedModule {}
