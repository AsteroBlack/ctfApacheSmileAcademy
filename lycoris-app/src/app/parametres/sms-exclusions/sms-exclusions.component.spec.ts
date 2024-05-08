import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SmsExclusionsComponent } from './sms-exclusions.component';

describe('SmsExclusionsComponent', () => {
  let component: SmsExclusionsComponent;
  let fixture: ComponentFixture<SmsExclusionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SmsExclusionsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SmsExclusionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
