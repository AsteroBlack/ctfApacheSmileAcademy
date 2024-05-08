import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalUpdatePasswordComponent } from './modal-update-password.component';

describe('ModalUpdatePasswordComponent', () => {
  let component: ModalUpdatePasswordComponent;
  let fixture: ComponentFixture<ModalUpdatePasswordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalUpdatePasswordComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalUpdatePasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
