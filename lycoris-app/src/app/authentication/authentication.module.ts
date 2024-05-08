import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { AuthenticationRoutingModule } from "./authentication-routing.module";
import { SigninComponent } from "./signin/signin.component";
import { ReactiveFormsModule } from "@angular/forms";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button";
import { ModalUpdatePasswordComponent } from './signin/modal-update-password/modal-update-password.component';
import { ModalForgotPasswordComponent } from './signin/modal-forgot-password/modal-forgot-password.component';
import { SharedModule } from "../shared/shared.module";
import { ModalDuoComponent } from './signin/modal-duo/modal-duo.component';
@NgModule({
  declarations: [
    SigninComponent,
    ModalUpdatePasswordComponent,
    ModalForgotPasswordComponent,
    ModalDuoComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    AuthenticationRoutingModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    SharedModule
  ],
})
export class AuthenticationModule {}
