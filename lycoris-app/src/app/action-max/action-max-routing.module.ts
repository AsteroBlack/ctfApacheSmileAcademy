import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ActionMaxComponent } from './action-max.component';
import { BlocageMasseComponent } from './blocage-masse/blocage-masse.component';
import { MiseEnMachineDeMasseComponent } from './mise-en-machine-de-masse/mise-en-machine-de-masse.component';
import { BlocageAutomatiqueComponent } from './blocage-automatique/blocage-automatique.component';
import { DeblocageMasseComponent } from './deblocage-masse/deblocage-masse.component';

const routes: Routes = [
  {
    path: "blocage-masse",
    component: BlocageMasseComponent,
  },
  {
    path: "mise-en-machine-de-masse",
    component: MiseEnMachineDeMasseComponent,
  },
  {
    path: "blocage-automatique",
    component: BlocageAutomatiqueComponent
  },
  {
    path: "deblocage-masse",
    component: DeblocageMasseComponent
  }
];



@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ActionMaxRoutingModule { }
