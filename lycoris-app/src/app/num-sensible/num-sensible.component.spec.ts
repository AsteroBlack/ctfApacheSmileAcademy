import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NumSensibleComponent } from './num-sensible.component';

describe('NumSensibleComponent', () => {
  let component: NumSensibleComponent;
  let fixture: ComponentFixture<NumSensibleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NumSensibleComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NumSensibleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
