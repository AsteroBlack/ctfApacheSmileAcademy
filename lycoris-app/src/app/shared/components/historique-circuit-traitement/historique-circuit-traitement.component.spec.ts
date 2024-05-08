import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoriqueCircuitTraitementComponent } from './historique-circuit-traitement.component';

describe('HistoriqueCircuitTraitementComponent', () => {
  let component: HistoriqueCircuitTraitementComponent;
  let fixture: ComponentFixture<HistoriqueCircuitTraitementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HistoriqueCircuitTraitementComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HistoriqueCircuitTraitementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
