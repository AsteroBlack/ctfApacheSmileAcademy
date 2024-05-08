import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatExpansionModule } from '@angular/material/expansion';
import { ServiceTechniqueRoutingModule } from './historique-routing.module';
import { MainHistoriqueComponent } from './main-historique/main-historique.component';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { LogConnectionComponent } from './log-connection/log-connection.component';
import { LogActionNumeroComponent } from './log-action-numero/log-action-numero.component';
import { CoreModule } from '../core/core.module';
import { LogActionComponent } from './log-action/log-action.component';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table'; 
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatPaginatorModule } from '@angular/material/paginator';
import { LogActionPersoComponent } from './log-action-perso/log-action-perso.component';
import { LogAlertComponent } from './log-alert/log-alert.component';

@NgModule({
  declarations: [
    MainHistoriqueComponent,
    LogConnectionComponent,
    LogActionNumeroComponent,
    LogActionComponent,
    LogActionPersoComponent,
    LogAlertComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    MatExpansionModule,
    ServiceTechniqueRoutingModule,
    SharedModule,
    CoreModule,
    MatCardModule,
    MatTableModule,
    MatInputModule,
    MatFormFieldModule,
    MatPaginatorModule
  ]
})
export class ServiceTechniqueModule { }
