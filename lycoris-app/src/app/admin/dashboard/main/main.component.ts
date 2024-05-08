import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription, switchMap, tap, of } from 'rxjs';
import { DashboardService } from 'src/app/core/service/dashboard.service';
import { DashboardSectionsDatas } from './dashboard.model';
import { AuthService } from 'src/app/core/service/auth.service';
import { TypeNumeroService } from 'src/app/core/service/type-numero.service';
import { TypeNumeroLabel, ResponseTypeNumero } from 'src/app/core/models/type-numero.model';
import { RestClientService } from 'src/app/core/service/rest-client.service';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import * as moment from 'moment';


@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss'],
})
export class MainComponent implements OnInit, OnDestroy {
  isActiveDate = false
  busyGet: Subscription;
  periodSubscription: Subscription;
  //dashboardSectionsDatas: DashboardSectionsDatas
  dashboardSectionsDatas: any = {
    omci: {
      uniques: {
        unlocked: {},
        locked: {},
        inMachine: {},
        TOTAL: {},
      },
      TOTALS: {
        unlocked: {},
        locked: {},
        inMachine: {},
        TOTAL: {},
      },
    },
    telco: {
      uniques: {
        unlocked: {},
        locked: {},
        inMachine: {},
        TOTAL: {},
      },
      TOTALS: {
        unlocked: {},
        locked: {},
        inMachine: {},
        TOTAL: {},
      },
    },
  };
  
  user: any
  userProfil: String
  endPoint= 'actionUtilisateur/'
  userFonctionnalites: Array<any>= []
  categorieUser: string= ""
  private typeNumeros: ResponseTypeNumero[]
  searchData: any = {
    start: '',
    end: '',
  };
  dashboardInfosCards = {
    uniques: [
      {
        key: "TOTAL",
        label: "Nombre de",
        label2: "numéro gérés",
        icon: "delete_sweep",
        leftValueLabel: "BSCS",
        rightValueLabel: "CORAIL",
      },
      {
        key: "unlocked",
        label: "Nombre de fois",
        label2: "de déblocage(s)",
        icon: "vpn_key",
        leftValueLabel: "BSCS",
        rightValueLabel: "CORAIL",
      },
      {
        key: "inMachine",
        label: "Nombre de fois",
        label2: "de mise en machine",
        icon: "directions_railway",
        leftValueLabel: "BSCS",
        rightValueLabel: "CORAIL",
      },
      {
        key: "locked",
        label: "Nombre de fois",
        label2: "bloqué(s)",
        icon: "lock",
        leftValueLabel: "BSCS",
        rightValueLabel: "CORAIL",
      }
    ],
    TOTALS: [
      {
        key: "TOTAL",
        label: "Nombre de",
        label2: "numéro gérés",
        icon: "delete_sweep",
        leftValueLabel: "BSCS",
        rightValueLabel: "CORAIL",
      },
      {
        key: "unlocked",
        label: "Nombre de",
        label2: "déblocage(s)",
        icon: "vpn_key",
        leftValueLabel: "BSCS",
        rightValueLabel: "CORAIL",
      },
      {
        key: "inMachine",
        label: "Nombre de",
        label2: "mise en machine",
        icon: "directions_railway",
        leftValueLabel: "BSCS",
        rightValueLabel: "CORAIL",
      },
      {
        key: "locked",
        label: "Nombre de",
        label2: "blocage(s)",
        icon: "lock",
        leftValueLabel: "BSCS",
        rightValueLabel: "CORAIL",
      }
    ]
  }

  constructor(
    public dashboardServ: DashboardService,
    private authService: AuthService,
    private typeNumeroService: TypeNumeroService,
    private restClient: RestClientService,
    private utilities: UtilitiesService
    ) { 
      this.user= authService.currentUserValue,
      this.userProfil= authService.currentUserValue.profilLibelle
      this.userFonctionnalites= authService.currentUserValue.fonctionnaliteData
      this.categorieUser= authService.currentUserValue.categoryCode ?? ""
  }

  ngOnInit() {
    //this.getDashboardData()
    this.getDashboardItems()
  }
  ngOnDestroy(): void {
    //this.periodSubscription.unsubscribe();
  }

  onPeriodeEmitted(event) {
    if (this.isActiveDate) {
      //this.dashboardServ.updatePeriod(event);
      console.log('event', event);
      this.searchData.start = moment(event.start).format('DD/MM/YYYY HH:mm:ss');
      this.searchData.end = moment(event.end).format('DD/MM/YYYY HH:mm:ss');
      this.getDashboardItems()
      console.log('start', this.searchData.start);
      console.log('end', this.searchData.end);
    }
  }

  getObjectKeys(myObject: any) {
    return Object.keys(myObject);
  }

  getDashboardKpiValueToShow(value: number | string) {
    if (value && value != undefined) {
      return value
    }
    return 0
  }

  getCodeGranted(dashboardInfosCard: any, tabCards: Array<any>){
    let code= ""
    let length= tabCards.length
    if(dashboardInfosCard.label2 === 'mise en machine' || dashboardInfosCard.label2 === 'de mise en machine'){
      code= 'FUNC-ZALB'
      const hasCode= this.userFonctionnalites.some(item => item.code === code)
      if(!hasCode)length--
    }
    else if(dashboardInfosCard.label2 === 'déblocage(s)' || dashboardInfosCard.label2 === 'de déblocage(s)'){
      code= 'FUNC-UZZL'
      const hasCode= this.userFonctionnalites.some(item => item.code === code)
      if(!hasCode)length--
    }
    else if(dashboardInfosCard.label2 === 'blocage(s)' || dashboardInfosCard.label2 === 'bloqué(s)'){
      code= 'FUNC-OA3B'
      const hasCode= this.userFonctionnalites.some(item => item.code === code)
      if(!hasCode)length--
    }
    else if(dashboardInfosCard.label2 === 'numéro gérés'){
      code= 'FUNC-RIP0'
      const hasCode= this.userFonctionnalites.some(item => item.code === code)
      if(!hasCode)length--
    }

    console.log("userProprieties", {code: code, taille: length})
    console.log("array", {tab: tabCards, length: tabCards.length})

    return {code: code, taille: length}
    
  }


  getCodeCategory(dashboardKey: string){
    let code= ""
    console.log("categorieCode", dashboardKey)
    if(this.userProfil === 'RESPONSABLE BACK-OFFICE' && dashboardKey === 'OMCI'){
      code= "dd"
    }else if(this.userProfil === 'RESPONSABLE BACK-OFFICE' && dashboardKey === 'TELCO'){
      code= 'dd'
    }

    return code
  }

  getTypeNumeros() {
    if (this.typeNumeros === undefined) {
      return this.typeNumeroService.getList().pipe(
        tap(response => {
          if(response.length>=2){
            this.typeNumeros = response;
            console.log("typeNum", this.typeNumeros)
          }
          else{
            throw new Error("less than 2 type numeros finded whereas need at least 2 type numeros for the next requests")
          }
        })
      )
    }
    return of([])
  }

  getTypeNumero(label: TypeNumeroLabel) {
    return this.typeNumeros.find(typeNumero => typeNumero.libelle === label);
  }

  getDashboardData(){
      this.busyGet = this.periodSubscription = this.dashboardServ.period$.pipe(
        tap(() => this.dashboardSectionsDatas = undefined),
        switchMap(() => this.dashboardServ.getDashboardDatas())
      ).subscribe({
        next: (dashboardSectionsDatas: DashboardSectionsDatas) => {
          this.dashboardSectionsDatas = dashboardSectionsDatas;

        },
      })

      console.log("hey")
  }

 

  getDashboardItems(param?: any) {
    const currentDate = moment().format('DD/MM/YYYY HH:mm:ss');
    const req = {
      user: this.user.id,
      data: {
        createdAt: param?.start || currentDate,
        createdAtParam: {
          operator: "[]",
          start: this.searchData?.start || currentDate,
          end: this.searchData?.end || currentDate,
        },
        typeNumeroId: null
      }
    };

    this.busyGet = this.getTypeNumeros().pipe(
      tap(() => {
        req.data.typeNumeroId = this.getTypeNumero(TypeNumeroLabel.CORAIL).id;
        this.resetDataForSystem('CORAIL'); // Réinitialiser seulement pour CORAIL
      }),
      switchMap(() => this.fetchDashboardData(req)),
      tap((res) => {
        this.updateDashboardData('CORAIL', res);
      }),
      tap(() => {
        req.data.typeNumeroId = this.getTypeNumero(TypeNumeroLabel.BSCS).id;
        this.resetDataForSystem('BSCS'); // Réinitialiser seulement pour BSCS
      }),
      switchMap(() => this.fetchDashboardData(req)),
    ).subscribe((res) => {
      this.updateDashboardData('BSCS', res);
    });
  }

  private fetchDashboardData(requestData: any) {
    return this.restClient.post(this.endPoint + 'dashboard', requestData);
  }


  private updateDashboardData(system: string, responseData: any) {
    // Vérifier la validité de la réponse
    if (!responseData || responseData.hasError || !Array.isArray(responseData.item)) {
      console.log("Réponse non valide ou contenant une erreur.");
      return; // Sortie anticipée si les données ne sont pas valides
    }
  
    // Traiter la réponse de l'API
    responseData.item.forEach(dataCategory => {
      const systemType = dataCategory.key === "NSIMSWAP" ? "uniques" : "TOTALS";
  
      dataCategory.values.forEach(category => {
        const categoryType = category.key.toLowerCase(); // omci ou telco
        
        // S'assurer que la catégorie est présente avant de traiter
        if (this.dashboardSectionsDatas[categoryType]) {
          category.values.forEach(dataPoint => {
            const dataType = this.mapDataType(dataPoint.key); // unlocked, locked, inMachine, TOTAL
            const value = Number(dataPoint.value); // Convertir la valeur en nombre
  
            // Initialiser les valeurs si non présentes
            if (!this.dashboardSectionsDatas[categoryType][systemType][dataType][system]) {
              this.dashboardSectionsDatas[categoryType][systemType][dataType][system] = 0;
            }
  
            // Mise à jour des données pour le système spécifié (CORAIL ou BSCS)
            this.dashboardSectionsDatas[categoryType][systemType][dataType][system] += value;
          });
        }
      });
    });
  
    // Recalculer les totaux après mise à jour, si nécessaire
    this.updateTotalForCorailAndBscs();
  }
  

  private resetDataForSystem(system: string) {
    // Structure de réinitialisation par défaut pour un système
    const defaultStructure = {
      unlocked: 0,
      locked: 0,
      inMachine: 0,
      TOTAL: 0,
    };
  
    ['omci', 'telco'].forEach(section => {
      ['uniques', 'TOTALS'].forEach(type => {
        Object.keys(this.dashboardSectionsDatas[section][type]).forEach(state => {
          this.dashboardSectionsDatas[section][type][state][system] = defaultStructure[state];
        });
      });
    });
  }
  
  
  
  private mapDataType(key: string): string {
    switch (key) {
      case "DEBLOQUER":
        return "unlocked";
      case "BLOQUER":
        return "locked";
      case "MISE_EN_MACHINE":
        return "inMachine";
      case "TOTAL":
        return "TOTAL";
      default:
        console.error(`Unrecognized key: ${key}`);
        return "";
    }
  }
  
  
  private updateTotalForCorailAndBscs() {
    const sections = ['omci', 'telco'];
    const dataTypes = ['uniques', 'TOTALS'];
  
    sections.forEach(section => {
      dataTypes.forEach(dataType => {
        const sectionData = this.dashboardSectionsDatas[section][dataType];
        
        Object.keys(sectionData).forEach(property => {
          // Initialise le total à zéro
          sectionData[property]['TOTAL'] = 0;
  
          // Convertit les valeurs de CORAIL et BSCS en nombres et les additionne
          if (sectionData[property]['CORAIL'] !== undefined) {
            sectionData[property]['TOTAL'] += Number(sectionData[property]['CORAIL']);
          }
          if (sectionData[property]['BSCS'] !== undefined) {
            sectionData[property]['TOTAL'] += Number(sectionData[property]['BSCS']);
          }
        });
      });
    });
  }
  
  
  


}
