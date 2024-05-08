import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RechercheNumeroComponent } from './recherche-numero.component';

describe('RechercheNumeroComponent', () => {
  let component: RechercheNumeroComponent;
  let fixture: ComponentFixture<RechercheNumeroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RechercheNumeroComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RechercheNumeroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
