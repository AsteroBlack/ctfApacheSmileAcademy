import { Injectable } from '@angular/core';
import {
  Router,
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  CanLoad,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';

import { AuthService } from '../service/auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate, CanLoad {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const isSignedIn = this.authService.currentUserValue;
    console.log("isSignedIn",isSignedIn);
    
    if (isSignedIn) {
      const userRole = this.authService.currentUserValue.fonctionnaliteData;
    //   // if (route.data.role && route.data.role.indexOf(userRole) === -1) {
    //   //   this.router.navigate(['/authentication/signin']);
    //   //   return false;
    //   // }
      return true;
    }

    this.router.navigate(['/authentication/signin']);
    return false;
  }

  canLoad(): boolean {
    if (this.authService.currentUserValue) {
      return true;
    } else {
      console.log('ddddd');

      this.router.navigate(['/authentication/signin']);
      return false;
    }
  }
}
