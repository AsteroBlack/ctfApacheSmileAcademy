import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MiseEnMachineDeMasseComponent } from './mise-en-machine-de-masse.component';

describe('MiseEnMachineDeMasseComponent', () => {
  let component: MiseEnMachineDeMasseComponent;
  let fixture: ComponentFixture<MiseEnMachineDeMasseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MiseEnMachineDeMasseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MiseEnMachineDeMasseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
