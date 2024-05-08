import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardInfosCardComponent } from './dashboard-infos-card.component';

describe('DashboardInfosCardComponent', () => {
  let component: DashboardInfosCardComponent;
  let fixture: ComponentFixture<DashboardInfosCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardInfosCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardInfosCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
