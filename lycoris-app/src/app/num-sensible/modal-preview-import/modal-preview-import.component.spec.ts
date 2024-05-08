import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalPreviewImportComponent } from './modal-preview-import.component';

describe('ModalPreviewImportComponent', () => {
  let component: ModalPreviewImportComponent;
  let fixture: ComponentFixture<ModalPreviewImportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalPreviewImportComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalPreviewImportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
