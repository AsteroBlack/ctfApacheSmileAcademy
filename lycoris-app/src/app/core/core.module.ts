import { NgModule, Optional, SkipSelf } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RightSidebarService } from './service/rightsidebar.service';
import { AuthGuard } from './guard/auth.guard';
import { AuthService } from './service/auth.service';
import { DynamicScriptLoaderService } from './service/dynamic-script-loader.service';
import { throwIfAlreadyLoaded } from './guard/module-import.guard';
import { RestClientService } from './service/rest-client.service';
import { BsModalService, ModalModule } from 'ngx-bootstrap/modal';
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { PermissionDirective } from './directives/permission.directive';

@NgModule({
  declarations: [
    PermissionDirective
  ],
  imports: [CommonModule,ModalModule.forRoot(),MatSnackBarModule],
  providers: [
    RightSidebarService,
    AuthGuard,
    AuthService,
    DynamicScriptLoaderService,
    RestClientService,
    BsModalService
  ],
  exports: [PermissionDirective],
})
export class CoreModule {
  // constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
  //   throwIfAlreadyLoaded(parentModule, 'CoreModule');
  // }
}
