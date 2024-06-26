import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
@Injectable()
export class RightSidebarService {
  public sidebarSubject = new BehaviorSubject<boolean>(false);
  sidebarState = this.sidebarSubject.asObservable();

  setRightSidebar = (value: boolean) => {
    this.sidebarSubject.next(value);
  };

  constructor() {}
}
