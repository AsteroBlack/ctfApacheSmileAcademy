import { Injectable } from "@angular/core";
import { User } from "../models/user";
import { RestClientService } from "./rest-client.service";
import { AuthService } from "./auth.service";
import { RequestBodyGetList, ResponseGetList } from "../models/type-numero.model";
import { catchError, map, throwError } from "rxjs";
import { UtilitiesService } from "./utilities.service";

@Injectable({
  providedIn: "root"
})
export class TypeNumeroService{
  private user: User;

  constructor(private restClientService: RestClientService, authServ: AuthService, private utilities: UtilitiesService){
    this.user = authServ.currentUserValue
  }

  getList(){
    const bodyRequest: RequestBodyGetList = {
      user: this.user.id,
      isSimpleLoading: false,
      data: {}
    }

    return this.restClientService.post('typeNumero/getByCriteria', bodyRequest).pipe(
      this.restClientService.responseHasErrorOperator(),
      catchError(err => {
        this.utilities.showRequestErrorNotification(err, 'Get Type Numero list')
        return throwError(() => err)
      }),
      map((response: ResponseGetList)=>response.items)
    );
  }
}
