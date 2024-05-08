import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeDemandesComponent } from './liste-demandes.component';

describe('ListeDemandesComponent', () => {
  let component: ListeDemandesComponent;
  let fixture: ComponentFixture<ListeDemandesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListeDemandesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListeDemandesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
