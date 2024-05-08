import { DiffusionListComponent } from './diffusion-list/diffusion-list.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FonctionnalitesComponent } from './fonctionnalites/fonctionnalites.component';
import { RolesComponent } from './roles/roles.component';
import { UtilisateursComponent } from './utilisateurs/utilisateurs.component';
import { WorkflowCircuitComponent } from './workflow-circuit/workflow-circuit.component';
import { SmsExclusionsComponent } from './sms-exclusions/sms-exclusions.component';

const routes: Routes = [
  { path: '', redirectTo: 'utilisateurs', pathMatch: 'full' },
  {
    path: 'utilisateurs',
    component: UtilisateursComponent,
  },
  {
    path: 'roles',
    component: RolesComponent,
  },
  {
    path: 'fonctionnalites',
    component: FonctionnalitesComponent,
  },
  {
    path: 'circuit-validation',
    component: WorkflowCircuitComponent,
  },
  {
    path: 'liste-diffusions',
    component: DiffusionListComponent,
  },
  {
    path: 'sms-exclusions',
    component: SmsExclusionsComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdministrationRoutingModule { }
