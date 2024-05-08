import { Injectable } from '@angular/core';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { DiffusionInfos, RequestBodyCreateUpdateDiffusionInfos, RequestBodyDeleteDiffusionInfos, RequestBodyGetListDiffusionInfos, ResponseDefault, ResponseGetListDiffusionInfos } from './diffusion-list.model';
import { Observable, catchError, map, tap, throwError } from 'rxjs';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import { AuthService } from 'src/app/core/service/auth.service';
import { User } from 'src/app/core/models/user';
import { ParametrageGetListParametrages, RequestGetListParametrages, ResponseGetListParametrages } from './parametrages.model';

@Injectable({
  providedIn: 'root'
})
export class DiffusionListService {
  private readonly featureBasePath = "parametrageProfil";
  private currentUser: User;

  constructor(private restClientService: RestClientService, private utilities: UtilitiesService, private authService: AuthService) {
    this.authService.currentUser.subscribe({
      next: user => {
        this.currentUser = user
      }
    })
  }

  getList(searchTerm: string): Observable<ResponseGetListDiffusionInfos> {
    const datas: RequestBodyGetListDiffusionInfos = {
      user: this.currentUser.id,
      isSimpleLoading: false,
      data: {
        nomPrenom: searchTerm,
        email: searchTerm,
        numero: searchTerm
      }
    }

    return this.restClientService.post(`${this.featureBasePath}/getByCriteria`, datas).pipe(
      this.restClientService.responseHasErrorOperator(),
      // map((response: ResponseGetListDiffusionInfos) => response.items.map((item): DiffusionInfos => ({
      //     id: item.id,
      //     idParametrage: item.idParametrage,
      //     fullname: item.nomPrenom,
      //     email: item.email,
      //     tel: item.numero
      //   }))
      // ),
      map((response: ResponseGetListDiffusionInfos) => response),
      catchError(err => {
        this.utilities.showRequestErrorNotification(err, 'get diffusions list')
        return []
      }),
    );
  }

  getParametrageList(): Observable<ParametrageGetListParametrages[]> {
    const datas: RequestGetListParametrages = {
      user: this.currentUser.id,
      isSimpleLoading: false,
      data: {}
    }
    return this.restClientService.post("parametrage/getByCriteria", datas).pipe(
      this.restClientService.responseHasErrorOperator(),
      map((response: ResponseGetListParametrages) => response.items),
      catchError(err => {
        this.utilities.showRequestErrorNotification(err, 'get parametrages list')
        return []
      })
    )
  }

  create(diffusionInfos: DiffusionInfos) {
    const datas: RequestBodyCreateUpdateDiffusionInfos = {
      user: this.currentUser.id,
      datas: [{
        email: diffusionInfos.email,
        nomPrenom: diffusionInfos.fullname,
        numero: diffusionInfos.tel,
        idParametrage: diffusionInfos.idParametrage
      }]
    }
    return this.restClientService.post(`${this.featureBasePath}/create`, datas).pipe(
      this.restClientService.responseHasErrorOperator(),
      catchError(err => {
        this.utilities.showRequestErrorNotification(err, 'create diffusion')
        return throwError(() => err)
      }),
      tap(() => {
        this.utilities.showRequestSuccessNotification('créé avec succès')
      })
    );
  }

  update(diffusionInfos: DiffusionInfos) {
    const datas: RequestBodyCreateUpdateDiffusionInfos = {
      user: this.currentUser.id,
      datas: [{
        id: diffusionInfos.id,
        email: diffusionInfos.email,
        nomPrenom: diffusionInfos.fullname,
        numero: diffusionInfos.tel,
        idParametrage: diffusionInfos.idParametrage
      }]
    }
    return this.restClientService.post(`${this.featureBasePath}/update`, datas).pipe(
      this.restClientService.responseHasErrorOperator(),
      catchError(err => {
        this.utilities.showRequestErrorNotification(err, 'update diffusion')
        return throwError(() => err)
      }),
      tap(() => {
        this.utilities.showRequestSuccessNotification('Modifié avec succès')
      })
    );
  }

  delete(id: number) {
    const datas: RequestBodyDeleteDiffusionInfos = {
      user: this.currentUser.id,
      datas: [{
        id
      }]
    }
    return this.restClientService.post(`${this.featureBasePath}/delete`, datas).pipe(
      this.restClientService.responseHasErrorOperator(),
      catchError(err => {
        this.utilities.showRequestErrorNotification(err, 'delete diffusion')
        return throwError(() => err)
      }),
      tap(() => {
        this.utilities.showRequestSuccessNotification('Supprimé avec succès')
      })
    );
  }
}
