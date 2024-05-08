import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogConnectionComponent } from './log-connection.component';

describe('LogConnectionComponent', () => {
  let component: LogConnectionComponent;
  let fixture: ComponentFixture<LogConnectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LogConnectionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LogConnectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
