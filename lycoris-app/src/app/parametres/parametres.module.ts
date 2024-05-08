import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdministrationRoutingModule } from './parametres-routing.module';
import { UtilisateursComponent } from './utilisateurs/utilisateurs.component';
import { RolesComponent } from './roles/roles.component';
import { FonctionnalitesComponent } from './fonctionnalites/fonctionnalites.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { ComponentsModule } from '../shared/components/components.module';
import { SharedModule } from '../shared/shared.module';
import { WorkflowCircuitComponent } from './workflow-circuit/workflow-circuit.component';
import { FormCircuitValidationComponent } from './workflow-circuit/form-circuit-validation/form-circuit-validation.component';
import { NgSelectModule } from '@ng-select/ng-select'
import { CoreModule } from '../core/core.module';
import { DiffusionListComponent } from './diffusion-list/diffusion-list.component';
import { SmsExclusionsComponent } from './sms-exclusions/sms-exclusions.component';

@NgModule({
  declarations: [
    UtilisateursComponent,
    RolesComponent,
    FonctionnalitesComponent,
    WorkflowCircuitComponent,
    FormCircuitValidationComponent,
    DiffusionListComponent,
    SmsExclusionsComponent,
  ],
  imports: [
    CommonModule,
    AdministrationRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MatCheckboxModule,
    ComponentsModule,
    SharedModule,
    CoreModule,
    NgSelectModule
  ]
})
export class AdministrationModule { }
