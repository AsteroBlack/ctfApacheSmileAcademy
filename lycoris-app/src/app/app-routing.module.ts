import { DemandeModule } from './demande/demande.module';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthLayoutComponent } from "./layout/app-layout/auth-layout/auth-layout.component";
import { MainLayoutComponent } from "./layout/app-layout/main-layout/main-layout.component";
import { AuthGuard } from "./core/guard/auth.guard";
import { Role } from "./core/models/role";
import { Page404Component } from "./authentication/page404/page404.component";

const routes: Routes = [
  {
    path: "",
    component: MainLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      //{ path: "", redirectTo: "/authentication/signin", pathMatch: "full" },
      {
        path: "admin",
        canActivate: [AuthGuard],
        loadChildren: () =>
          import("./admin/admin.module").then((m) => m.AdminModule),
      },
      {
        path: "num-sensible",
        canActivate: [AuthGuard],
        loadChildren: () =>
          import("./num-sensible/num-sensible.module").then(
            (m) => m.NumSensibleModule
          ),
      },
      {
        path: "demande",
        canActivate: [AuthGuard],
        loadChildren: () =>
          import("./demande/demande.module").then(
            (m) => m.DemandeModule
          ),
      },
      {
        path: "action-max",
        canActivate: [AuthGuard],
        loadChildren: () =>
          import("./action-max/action-max.module").then(
            (m) => m.ActionMaxModule
          ),
      },
      {
        path: "parametres",
        canActivate: [AuthGuard],
        loadChildren: () =>
          import("./parametres/parametres.module").then(
            (m) => m.AdministrationModule
          ),
      },
      {
        path: "logs",
        canActivate: [AuthGuard],
        loadChildren: () =>
          import("./historique/historique.module").then(
            (m) => m.ServiceTechniqueModule
          ),
      },
      {
        path: "actions-uniques",
        canActivate: [AuthGuard],
        loadChildren: () =>
          import("./actions-uniques/actions-uniques.module").then(
            (m) => m.ActionsUniquesModule
          )
      },
      {
        path: "liste-demandes",
        canActivate: [AuthGuard],
        loadChildren: () =>
          import("./liste-demandes/liste-demandes.module").then(
            (m) => m.ListDemandesModule
          )
      }
    ],
  },
  {
    path: "authentication",
    component: AuthLayoutComponent,
    loadChildren: () =>
      import("./authentication/authentication.module").then(
        (m) => m.AuthenticationModule
      ),
  },
  { path: "**", component: Page404Component },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: "legacy" })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
