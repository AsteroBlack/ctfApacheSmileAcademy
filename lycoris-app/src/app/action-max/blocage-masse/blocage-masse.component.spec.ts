import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BlocageMasseComponent } from './blocage-masse.component';

describe('BlocageMasseComponent', () => {
  let component: BlocageMasseComponent;
  let fixture: ComponentFixture<BlocageMasseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BlocageMasseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BlocageMasseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
