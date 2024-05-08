import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RechercheNumUniqueComponent } from './recherche-num-unique.component';

describe('RechercheNumUniqueComponent', () => {
  let component: RechercheNumUniqueComponent;
  let fixture: ComponentFixture<RechercheNumUniqueComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RechercheNumUniqueComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RechercheNumUniqueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
