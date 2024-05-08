import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { map, timeout } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';
import { catchError, tap } from 'rxjs/operators';

export interface HttpDefaultResponse{
  status: {
    code?: string;
    message?: string;
  },
  hasError: boolean,
}

@Injectable({
  providedIn: 'root',
})
export class RestClientService {
  url: any;
  user: any;
  response: {ipAdress: any, machine: any}
  constructor(private http: HttpClient, private authService: AuthService) {
    this.url = environment.apiUrl;
    this.user = this.authService.currentUserValue;
  }

  get<T>(endpoint: string, params?: any, options?: any) {
    return this.http.get<T>(this.url + '' + endpoint, options);
  }

  post<T=any>(endpoint: string, body: any, option?: any): Observable<T> {
    let headers = new HttpHeaders();
      headers =headers.set('user', this.user.id.toString()); //this.user.id.toString()
    return this.http
      .post<T>(this.url + endpoint, body, {headers: headers})
      .pipe(timeout(1000 * 60 * 3));
  }

  responseHasErrorOperator(){
    return (observable: Observable<HttpDefaultResponse>) => observable.pipe(
      map(response=>{
        if(response.hasError){
          throw response.status.message;
        }
        return response;
      })
    )
  }

  getIpAdress(){
    return this.http.get<any>('https://geolocation-db.com/json/')
  }


  // getComputerName(){
  //   try {
  //     var network = new ActiveXObject('WScript.Network');
  //     alert(network.computerName);
  // }
  // catch (e) { }
  // }
}
