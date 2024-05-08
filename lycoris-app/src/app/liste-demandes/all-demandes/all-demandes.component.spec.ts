import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllDemandesComponent } from './all-demandes.component';

describe('AllDemandesComponent', () => {
  let component: AllDemandesComponent;
  let fixture: ComponentFixture<AllDemandesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllDemandesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllDemandesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
