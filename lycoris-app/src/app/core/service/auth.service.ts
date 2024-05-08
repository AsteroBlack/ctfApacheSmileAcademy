import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from '../models/user';
import { environment } from 'src/environments/environment';
import * as _ from "lodash";
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;
  //public user= JSON.parse(sessionStorage.getItem('currentUser') || '{}')

  constructor(private http: HttpClient, private router: Router) {
    // this.currentUserSubject = new BehaviorSubject<User|null>(JSON.parse(sessionStorage.getItem("currentUser") || '{}'));
    // this.currentUser = this.currentUserSubject.asObservable();
    const user= JSON.parse(sessionStorage.getItem('currentUser') || '{}')
  }

  public get currentUserValue(): User {
    return JSON.parse(sessionStorage.getItem('currentUser') || '{}');
  }

  /*public  setCurrentUser() {
    // Je viens de setter
     this.currentUserSubject.next(JSON.parse(sessionStorage.getItem('currentUser') || '{}'));
  }*/

  // public get userPermissions(){
  //   const permissions= {
  //     visualiserNum: this.currentUserValue.fonctionnaliteData.filter((val: any) => val.fonctionnaliteLibelle == 'VISUALISER NUMERO')[0].fonctionnaliteCode,
  //     visualiserDem: this.currentUserValue.fonctionnaliteData.filter((val: any) => val.fonctionnaliteLibelle == 'VISUALISER DEMANDE')[0].fonctionnaliteCode,
  //     actionSimSwap: this.currentUserValue.fonctionnaliteData.filter((val: any) => val.fonctionnaliteLibelle == 'ACTION SIM-SWAP')[0].fonctionnaliteCode,
  //     dashboard: this.currentUserValue.fonctionnaliteData.filter((val: any) => val.fonctionnaliteLibelle == 'DASHBOARD')[0].fonctionnaliteCode,
  //     validerLevBloc: this.currentUserValue.fonctionnaliteData.filter((val: any) => val.fonctionnaliteLibelle == 'VALIDER LEVÉE DE BLOCAGE ')[0].fonctionnaliteCode
  //   }
  //   return permissionss
  // }

  login(login: string, password: string, hostName: any, ipAddress: any) {
    let request={
      ipAddress: ipAddress,
      hostName: hostName,
      data: {
        login: login,
        password: password,
      }
    }
    return this.http
      .post<any>(`${environment.apiUrl}user/connexionLdap`, request)
      .pipe(
        map((user:any) => {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          if(user && user.items && user.items.length){
            // Faire remonter serviceId
            // if(user?.items && user?.items?.length > 0) {
            //   let currentUniteFonctionnelle = _.find(user.items[0]?.uniteFonctionnelles , (o:any) => { return o.idUniteFonctionnelle == user.items[0]?.uniteFoncActiveId });
            //   user.items[0].currentFonctionnaliteSelected = currentUniteFonctionnelle;
            // }
            sessionStorage.setItem('currentUser', JSON.stringify(user.items[0]));
            //this.currentUserSubject.next(user.items[0]);
          }
          return user;
        })
      );
  }

  logout() {
    // remove user from local storage to log user out
    sessionStorage.removeItem('currentUser');
    sessionStorage.clear();
    this.router.navigate(["/authentication/signin"]);
    //this.currentUserSubject.next({});
    //this.currentUserSubject.next(null);
    //return of({ success: false });
  }
}
