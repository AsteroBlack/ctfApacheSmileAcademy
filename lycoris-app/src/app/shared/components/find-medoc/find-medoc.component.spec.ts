import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FindMedocComponent } from './find-medoc.component';

describe('FindMedocComponent', () => {
  let component: FindMedocComponent;
  let fixture: ComponentFixture<FindMedocComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FindMedocComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FindMedocComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
