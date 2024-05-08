import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NumSensibleComponent } from './num-sensible.component';

const routes: Routes = [
  {
    path: "",
    component: NumSensibleComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NumSensibleRoutingModule { }
