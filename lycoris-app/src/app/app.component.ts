import { Component, HostListener } from '@angular/core';
import { Event, Router, NavigationStart, NavigationEnd } from "@angular/router";
import { PlatformLocation } from "@angular/common";
import { AutologoutService } from './core/service/autoLogout.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  currentUrl?: string;
  constructor(public _router: Router, location: PlatformLocation, private autoLogout: AutologoutService) {
    this._router.events.subscribe((routerEvent: Event) => {
      if (routerEvent instanceof NavigationStart) {
        this.currentUrl = routerEvent.url.substring(
          routerEvent.url.lastIndexOf("/") + 1
        );
      }
      if (routerEvent instanceof NavigationEnd) {
      }
      window.scrollTo(0, 0);
    });
  }


  @HostListener('window:mousemove') onMouseMove() {
    this.autoLogout.reset()
  }

  @HostListener('window:click') onClick() {
    this.autoLogout.reset()
  }
}
