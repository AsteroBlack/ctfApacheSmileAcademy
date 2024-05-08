import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpEvent,
  HttpRequest,
  HttpHandler
} from '@angular/common/http';

import { Observable } from 'rxjs';
import { AuthService } from '../service/auth.service';
import { User } from '../models/user';


@Injectable()
export class RequestInterceptor implements HttpInterceptor {

  user: User;
constructor(private authService: AuthService){
  this.user = this.authService.currentUserValue
}
  intercept(
    httpRequest: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    // httpRequest.body.data.uniteFoncId=JSON.parse(sessionStorage.getItem('currentUser'))?.uniteFoncActiveId||null
    console.log("httpRequest.body",httpRequest.body);
    // if(httpRequest.body && httpRequest.body.data){
    //   httpRequest.body.data.uniteFoncId=JSON.parse(sessionStorage.getItem('currentUser'))?.uniteFoncActiveId||null
    // }
    return next.handle(httpRequest.url.includes('authenticate') ? httpRequest : httpRequest.clone({
      
      body: {
        ...httpRequest.body,
        "user": this.user?.id || null
      }
    })
      )
  }
}
