import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-historique-circuit-traitement',
  templateUrl: './historique-circuit-traitement.component.html',
  styleUrls: ['./historique-circuit-traitement.component.scss']
})
export class HistoriqueCircuitTraitementComponent implements OnInit {

  @Input() circuitValidations : Array<any> = [];
  
  constructor() { }

  ngOnInit(): void {
  }

}
