import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BlocageComponent } from './blocage/blocage.component';
import { MiseEnMachineComponent } from './mise-en-machine/mise-en-machine.component';
import { TransfertCorbeilleComponent } from './transfert-corbeille/transfert-corbeille.component';
import { DeblocageComponent } from './deblocage/deblocage.component';
import { ActionsUniquesRoutingModule } from './actions-uniques-routing.module';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { ComponentsModule } from '../shared/components/components.module';
import { SharedModule } from '../shared/shared.module';
import { NgSelectModule } from '@ng-select/ng-select'
import { CoreModule } from '../core/core.module';
import {MatPaginatorModule} from '@angular/material/paginator';



@NgModule({
    declarations: [
        BlocageComponent,
        MiseEnMachineComponent,
        TransfertCorbeilleComponent,
        DeblocageComponent
    ],
    imports: [
        FormsModule,
        ReactiveFormsModule,
        MatButtonModule,
        MatCheckboxModule,
        MatExpansionModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        ComponentsModule,
        SharedModule,
        NgSelectModule,
        CoreModule,
        ActionsUniquesRoutingModule,
        MatPaginatorModule
    ]
})
export class ActionsUniquesModule{}