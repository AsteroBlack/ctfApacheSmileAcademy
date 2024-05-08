import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { AuthService } from "src/app/core/service/auth.service";
import { Role } from "src/app/core/models/role";
import { UnsubscribeOnDestroyAdapter } from "src/app/shared/UnsubscribeOnDestroyAdapter";
import { RestClientService } from "src/app/core/service/rest-client.service";
import { UtilitiesService } from "src/app/core/service/utilities.service";
import { Subscription } from "rxjs";
@Component({
  selector: "app-signin",
  templateUrl: "./signin.component.html",
  styleUrls: ["./signin.component.scss"],
})
export class SigninComponent
  extends UnsubscribeOnDestroyAdapter
  implements OnInit
{
  authForm?: FormGroup;
  submitted = false;
  loading = false;
  error = "";
  hide = true;
  busyGet?: Subscription;
  busySave?: Subscription;
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService, private restClient: RestClientService, private utilities: UtilitiesService
  ) {
    super();
  }

  ngOnInit() {
    this.authForm = this.formBuilder.group({
      username: ["admin@hospital.org", Validators.required],
      password: ["admin@123", Validators.required],
    });
  }
  get f() {
    return this.authForm?.controls;
  }
  adminSet() {
    this.authForm?.get("username")?.setValue("admin@hospital.org");
    this.authForm?.get("password")?.setValue("admin@123");
  }
  doctorSet() {
    this.authForm?.get("username")?.setValue("doctor@hospital.org");
    this.authForm?.get("password")?.setValue("doctor@123");
  }
  patientSet() {
    this.authForm?.get("username")?.setValue("patient@hospital.org");
    this.authForm?.get("password")?.setValue("patient@123");
  }
  onSubmit() {
    console.log('submit',this.authForm);
    let userName = this.authForm?.value.username
    let password = this.authForm?.value.password

    this.submitted = true;
    this.loading = true;
    this.error = "";
    if (this.authForm?.invalid) {
      this.error = "Username and Password not valid !";
      return;
    } else {
      let user={
        login: userName,
        password: password
      }
      var request = {
        data:user
      }
  
      this.busySave = this.restClient.post('user/login', request)
        .subscribe(
          (res:any) => {

            console.log("resul", res);
            this.loading = false;
  
            if (!res['hasError']) {
              this.router.navigate(["/admin/dashboard/main"]);
          }
          else{
            this.utilities.showNotification("snackbar-danger",
                  this.utilities.formatMsgServeur(res['status']['message']),
                  "bottom",
                  "center");
          }
          },
          err => {
            this.utilities.showNotification("snackbar-danger", this.utilities.getMessageEndPointNotAvailble(),
              "bottom",
              "center");
            this.loading = false;
          }
        );
      
      
      } } }
