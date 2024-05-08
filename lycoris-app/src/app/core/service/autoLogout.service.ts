import { Injectable } from "@angular/core";
import { AuthService } from "./auth.service";
import { Router } from "@angular/router";


@Injectable({
    providedIn: 'root',
})
export class AutologoutService{
    private timeOutId: any
    private readonly inactivityTimeout = 5 * 60 * 1000; // 5 minutes (en millisecondes)

    constructor(private authService: AuthService, private router: Router){

    }
  
    reset() {
      console.log("byyyye")
      clearTimeout(this.timeOutId);
      this.start();
    }
  
    start() {
      this.timeOutId = setTimeout(() => {
          this.authService.logout()          
      }, this.inactivityTimeout);
    }
}