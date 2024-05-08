import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';


@Injectable({
    providedIn: 'root'
  })
  export class MachineInfoService {
    constructor(@Inject(PLATFORM_ID) private platformId: Object) { }
  
    getMachineName(): string {
      if (isPlatformBrowser(this.platformId)) {
        // Code pour obtenir le nom de la machine ici
        const hostname = window.location.hostname;
        return hostname;
      } else {
        // Code de secours si l'application ne s'exécute pas dans un navigateur
        return 'Machine non disponible';
      }
    }
}
  