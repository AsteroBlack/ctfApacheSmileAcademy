import { NgModule } from '@angular/core';
import { FileUploadComponent } from './file-upload/file-upload.component';
import { BreadcrumbComponent } from './breadcrumb/breadcrumb.component';
import { SharedModule } from '../shared.module';
import { DetailsPatientComponent } from './details-patient/details-patient.component';
import { HistoriqueCircuitTraitementComponent } from './historique-circuit-traitement/historique-circuit-traitement.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';


@NgModule({
  declarations: [FileUploadComponent, BreadcrumbComponent, DetailsPatientComponent, ],
  imports: [SharedModule],
  exports: [FileUploadComponent, BreadcrumbComponent,DetailsPatientComponent],
})
export class ComponentsModule {}
