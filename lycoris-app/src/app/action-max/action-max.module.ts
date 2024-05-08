import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ActionMaxRoutingModule } from './action-max-routing.module';
import { ActionMaxComponent } from './action-max.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatIconModule } from '@angular/material/icon';
import { NgSelectModule } from '@ng-select/ng-select';
import { CoreModule } from '../core/core.module';
import { DemandeRoutingModule } from '../demande/demande-routing.module';
import { ComponentsModule } from '../shared/components/components.module';
import { SharedModule } from '../shared/shared.module';
import { ModalPreviewImportComponent } from './modal-preview-import/modal-preview-import.component';
import { BlocageMasseComponent } from './blocage-masse/blocage-masse.component';
import { MiseEnMachineDeMasseComponent } from './mise-en-machine-de-masse/mise-en-machine-de-masse.component';
import { BlocageAutomatiqueComponent } from './blocage-automatique/blocage-automatique.component';
import { DeblocageMasseComponent } from './deblocage-masse/deblocage-masse.component';


@NgModule({
  declarations: [
    ActionMaxComponent,
    ModalPreviewImportComponent,
    BlocageMasseComponent,
    MiseEnMachineDeMasseComponent,
    BlocageAutomatiqueComponent,
    DeblocageMasseComponent

  ],
  imports: [
    CommonModule,
    ActionMaxRoutingModule,
    DemandeRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MatIconModule,
    MatButtonModule,
    MatCheckboxModule,
    ComponentsModule,
    SharedModule,
    CoreModule,
    NgSelectModule
  ]
})
export class ActionMaxModule { }
