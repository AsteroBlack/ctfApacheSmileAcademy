import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainListeAccueilByEtapeComponent } from './main-liste-accueil-by-etape.component';

describe('MainListeAccueilByEtapeComponent', () => {
  let component: MainListeAccueilByEtapeComponent;
  let fixture: ComponentFixture<MainListeAccueilByEtapeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MainListeAccueilByEtapeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MainListeAccueilByEtapeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
