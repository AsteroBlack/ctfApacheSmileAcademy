import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NumSensibleRoutingModule } from './num-sensible-routing.module';
import { NumSensibleComponent } from './num-sensible.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatExpansionModule } from '@angular/material/expansion';
import { SharedModule } from '../shared/shared.module';
import { ModalPreviewImportComponent } from './modal-preview-import/modal-preview-import.component';
import { CoreModule } from '../core/core.module';
import { RechercheNumeroComponent } from './recherche-numero/recherche-numero.component';
import { RechercheNumUniqueComponent } from './recherche-num-unique/recherche-num-unique.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';


@NgModule({
  declarations: [
    NumSensibleComponent,
    ModalPreviewImportComponent,
    RechercheNumeroComponent,
    RechercheNumUniqueComponent
  ],
  imports: [
    CommonModule,
    NumSensibleRoutingModule,
    FormsModule,
    MatExpansionModule,
    SharedModule,
    CoreModule,
    MatPaginatorModule,
    MatSelectModule,
    MatOptionModule,
    ReactiveFormsModule
  ]
})
export class NumSensibleModule { }
