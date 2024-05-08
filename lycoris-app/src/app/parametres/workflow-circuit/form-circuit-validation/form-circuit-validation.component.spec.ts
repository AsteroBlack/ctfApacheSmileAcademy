import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormCircuitValidationComponent } from './form-circuit-validation.component';

describe('FormCircuitValidationComponent', () => {
  let component: FormCircuitValidationComponent;
  let fixture: ComponentFixture<FormCircuitValidationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormCircuitValidationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormCircuitValidationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
