import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { LogActionNumeroComponent } from "./log-action-numero/log-action-numero.component";
import { LogConnectionComponent } from "./log-connection/log-connection.component";
import { MainHistoriqueComponent } from "./main-historique/main-historique.component";
import { LogActionComponent } from "./log-action/log-action.component";
import { LogActionPersoComponent } from "./log-action-perso/log-action-perso.component";
import { LogAlertComponent } from "./log-alert/log-alert.component";

const routes: Routes = [
  {
    path: "main-historique",
    component: MainHistoriqueComponent,
  },
  {
    path: "log-connexion",
    component: LogConnectionComponent,
  },
  {
    path: "log-action-numero",
    component: LogActionNumeroComponent,
  },
  {
    path: "log-action",
    component: LogActionComponent
  },
  {
    path: "log-mes-actions",
    component: LogActionPersoComponent
  },
  {
    path: "log-alertes",
    component: LogAlertComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ServiceTechniqueRoutingModule {}
