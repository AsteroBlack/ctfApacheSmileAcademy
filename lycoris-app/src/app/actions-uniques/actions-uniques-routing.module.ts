import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DeblocageComponent } from './deblocage/deblocage.component';
import { BlocageComponent } from './blocage/blocage.component';
import { MiseEnMachineComponent } from './mise-en-machine/mise-en-machine.component';
import { TransfertCorbeilleComponent } from './transfert-corbeille/transfert-corbeille.component';


const routes: Routes= [
    {
        path: '', redirectTo: 'blocage', pathMatch: 'full'
    },
    {
        path: 'deblocage',
        component: DeblocageComponent
    },
    {
        path: 'blocage',
        component: BlocageComponent
    },
    {
        path: 'mise-en-machine',
        component: MiseEnMachineComponent
    },
    {
        path: 'transfert-corbeille',
        component: TransfertCorbeilleComponent
    }
]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ActionsUniquesRoutingModule{}