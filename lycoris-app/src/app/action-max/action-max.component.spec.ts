import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActionMaxComponent } from './action-max.component';

describe('ActionMaxComponent', () => {
  let component: ActionMaxComponent;
  let fixture: ComponentFixture<ActionMaxComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ActionMaxComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ActionMaxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
