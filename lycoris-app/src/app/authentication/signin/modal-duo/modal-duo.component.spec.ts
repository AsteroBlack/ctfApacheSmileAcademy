import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalDuoComponent } from './modal-duo.component';

describe('ModalDuoComponent', () => {
  let component: ModalDuoComponent;
  let fixture: ComponentFixture<ModalDuoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalDuoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalDuoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
