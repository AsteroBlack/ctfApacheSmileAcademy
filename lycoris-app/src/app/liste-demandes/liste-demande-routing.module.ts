import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { DemandeAccepteeComponent } from "./demande-acceptee/demande-acceptee.component";
import { DemandeRefuseeComponent } from "./demande-refusee/demande-refusee.component";
import { AllDemandesComponent } from "./all-demandes/all-demandes.component";



const routes: Routes= [
    {
        path: "demandes-en-attente",
        component: AllDemandesComponent
    },
    {
        path: "demandes-acceptées",
        component: DemandeAccepteeComponent
    },
    {
        path: "demandes-refusées",
        component: DemandeRefuseeComponent
    }
];


@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class ListDemandesRoutingModule {}