import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DemandeRefuseeComponent } from './demande-refusee.component';

describe('DemandeRefuseeComponent', () => {
  let component: DemandeRefuseeComponent;
  let fixture: ComponentFixture<DemandeRefuseeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DemandeRefuseeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DemandeRefuseeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
