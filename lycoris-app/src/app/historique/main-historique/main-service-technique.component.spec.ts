import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainHistoriqueComponent } from './main-historique.component';

describe('MainServiceTechniqueComponent', () => {
  let component: MainHistoriqueComponent;
  let fixture: ComponentFixture<MainHistoriqueComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MainHistoriqueComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MainHistoriqueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
