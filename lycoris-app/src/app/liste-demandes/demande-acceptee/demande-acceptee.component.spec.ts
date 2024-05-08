import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DemandeAccepteeComponent } from './demande-acceptee.component';

describe('DemandeAccepteeComponent', () => {
  let component: DemandeAccepteeComponent;
  let fixture: ComponentFixture<DemandeAccepteeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DemandeAccepteeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DemandeAccepteeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
