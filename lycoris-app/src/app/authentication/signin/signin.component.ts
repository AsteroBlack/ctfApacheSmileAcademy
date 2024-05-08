import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { AuthService } from "src/app/core/service/auth.service";
import { Role } from "src/app/core/models/role";
import { UnsubscribeOnDestroyAdapter } from "src/app/shared/UnsubscribeOnDestroyAdapter";
import { UtilitiesService } from "src/app/core/service/utilities.service";
import { BsModalRef, BsModalService } from "ngx-bootstrap/modal";
import { ModalUpdatePasswordComponent } from "./modal-update-password/modal-update-password.component";
import { ModalForgotPasswordComponent } from "./modal-forgot-password/modal-forgot-password.component";
import { ModalDuoComponent } from "./modal-duo/modal-duo.component";
import { RestClientService } from "src/app/core/service/rest-client.service";
import { Subscription } from 'rxjs';
import { MachineInfoService } from "src/app/core/service/machine-info.service";
@Component({
  selector: "app-signin",
  templateUrl: "./signin.component.html",
  styleUrls: ["./signin.component.scss"],
})
export class SigninComponent
  extends UnsubscribeOnDestroyAdapter
  implements OnInit
{
  authForm!: FormGroup;
  modalRef?: BsModalRef;
  submitted = false;
  loading = false;
  error = "";
  hide = true;
  password: any;
  busyGet: Subscription;
  hostName: any
  ipAdresse: any
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private utilities: UtilitiesService,
    private modalService: BsModalService,
    private restClient: RestClientService,
    private machineInfoService: MachineInfoService
  ) {
    super();
  }

  ngOnInit() {
    this.authForm = this.formBuilder.group({
      login: ["", Validators.required],
      password: ["", Validators.required],
    });
    this.hostName= this.machineInfoService.getMachineName()
    this.getIpAdress()
  }
  get f() {
    return this.authForm.controls;
  }

  getIpAdress(){
    this.busyGet= this.restClient.getIpAdress()
     .subscribe(
      {
        next: (res) =>{
          if(res){
            console.log("res IP", res.IPv4)
            this.ipAdresse= res.IPv4
          }else return
        },
        error: (err) => console.log(err)
      }
     )
  }

  onSubmit() {
    this.submitted = true;
    this.loading = true;
    this.error = "";
    if (this.authForm.invalid) {
      this.error = "Login and Password not valid !";
      return;
    } else {
      this.password=this.f["password"].value
      this.subs.sink = this.authService
        .login(this.f["login"].value, this.f["password"].value, this.hostName, this.ipAdresse)
        .subscribe(
          (res:any) => {
            if (res && !res['hasError']) {
              console.log('res authxx',res);
              //naviguer en fonction des abilitation later
              // if(res.items[0].isDefaultPassword){
              //   this.openModal()
              // }
              // else{
                //this.authService.setCurrentUser();
                if(res['items'][0]['profilLibelle'] === 'UTILISATEUR BACK-OFFICE'){
                  this.router.navigate(["/num-sensible"])
                }else if(res['items'][0]['profilLibelle'] ===  'ADMIN TECHNIQUE' || res['items'][0]['profilLibelle'] === 'GESTION SIM'){
                  this.router.navigate(["/liste-demandes/demandes-en-attente"])
                }else this.router.navigate(["/admin/dashboard/main"]);
                //this.openDuoModal()
              // }
              //this.router.navigate(['/admin/dashboard/main'])
              this.loading = false;
              
              
            } else {
              
              this.utilities.showNotification("snackbar-danger",
                this.utilities.formatMsgServeur(res['status']['message']),
                "bottom",
                "center");
              // this.error = res['status']['message'];
              this.loading = false;
            }
          },
          (error) => {
            // this.error = error;
            console.log('error',error);
            
            this.submitted = false;
            this.loading = false;
            this.utilities.showNotification("snackbar-danger",
              error,
                "bottom",
                "center");
          }
        );
    }
  }

  openModal(data? :any,type?:any) {
    let modal:any
    modal = ModalUpdatePasswordComponent
    this.modalRef = this.modalService.show(modal, {
      id: 1,
      class: 'modal-custom',
    });
    // if (data) this.modalRef.content.currentData = data;
    this.modalRef.onHide?.subscribe((res:any) => {
      console.log('hidden');
      // this.getData();
    });
  }

  forgotPassword(){
    let modal:any
    modal = ModalForgotPasswordComponent
    this.modalRef = this.modalService.show(modal, {
      id: 1,
      class: 'modal-custom',
      // data:boulangerie
    });
    // if (data) this.modalRef.content.currentData = data;
    this.modalRef.onHide?.subscribe((res) => {
      console.log('hidden');
      // this.getData();
    });
  }

  openDuoModal(data?:any){
    let modal:any
    modal = ModalDuoComponent
    this.modalRef = this.modalService.show(modal, {
      id: 1,
      class: 'modal-custom',
      // data:boulangerie
    });
    // data.password = this.password
    // if (data) {
    //   this.modalRef.content.currentData = data;
    // } 

    this.modalRef.onHide?.subscribe((res) => {
      console.log('hidden',res);
      // this.getData();
      
      // this.router.navigate(["/admin/dashboard/main"]);
    });
  }
}
