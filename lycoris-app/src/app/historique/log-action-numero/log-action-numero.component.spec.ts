import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogActionNumeroComponent } from './log-action-numero.component';

describe('LogActionNumeroComponent', () => {
  let component: LogActionNumeroComponent;
  let fixture: ComponentFixture<LogActionNumeroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LogActionNumeroComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LogActionNumeroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
