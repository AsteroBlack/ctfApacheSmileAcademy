import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DiffusionListComponent } from './diffusion-list.component';

describe('DiffusionListComponent', () => {
  let component: DiffusionListComponent;
  let fixture: ComponentFixture<DiffusionListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DiffusionListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DiffusionListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
