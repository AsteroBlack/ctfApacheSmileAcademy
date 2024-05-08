import {
  Directive,
  Input,
  TemplateRef,
  ViewContainerRef,
} from '@angular/core';
import { AuthService } from '../service/auth.service';

@Directive({
  selector: '[simSwapIsGranted]'
})
export class PermissionDirective {
  @Input() set simSwapIsGranted(permission: string) {
    this.isGranted(permission);
  }
  user: any = {};

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainerRef: ViewContainerRef,private authService: AuthService,) { 
    this.user = this.authService.currentUserValue;
  }

  private isGranted(permissions: string) {
      const isGranted = this.user.fonctionnaliteData.filter((val)=> val.code == permissions);
   
    if (isGranted.length > 0) {
      this.viewContainerRef.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainerRef.clear();
      
    }
  }
}
