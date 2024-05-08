import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

//import { TimePickerRoutingModule } from './virtual-keyboard-routing.module';
import { TimePickerComponent } from './time-picker.component';

@NgModule({
  declarations: [TimePickerComponent],
  imports: [
    CommonModule,
    //TimePickerRoutingModule
  ]
})
export class TimePickerModule { }
