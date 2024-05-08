// Sidebar route metadata
export interface RouteInfo {
  path: string;
  title: string;
  moduleName: string;
  iconType: string;
  icon: string;
  class: string;
  groupTitle: boolean;
  badge: string;
  badgeClass: string;
  role?: string[];
  isVisible?:Boolean;
  code?:string,
  submenu: RouteInfo[];
}
