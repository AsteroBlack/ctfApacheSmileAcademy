import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AllDemandesComponent } from './all-demandes/all-demandes.component';
import { DemandeAccepteeComponent } from './demande-acceptee/demande-acceptee.component';
import { DemandeRefuseeComponent } from './demande-refusee/demande-refusee.component';

import { ListDemandesRoutingModule } from './liste-demande-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { NgSelectModule } from '@ng-select/ng-select';
import { CoreModule } from '../core/core.module';
import { AdministrationRoutingModule } from '../parametres/parametres-routing.module';
import { ComponentsModule } from '../shared/components/components.module';
import { SharedModule } from '../shared/shared.module';
import { Ng2SearchPipeModule } from 'ng2-search-filter';




@NgModule({
    declarations: [
        AllDemandesComponent,
        DemandeAccepteeComponent,
        DemandeRefuseeComponent
    ],
    imports: [
        CommonModule,
        ListDemandesRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        MatIconModule,
        MatButtonModule,
        MatCheckboxModule,
        ComponentsModule,
        SharedModule,
        CoreModule,
        NgSelectModule,
        Ng2SearchPipeModule
    ]
})
export class ListDemandesModule {}