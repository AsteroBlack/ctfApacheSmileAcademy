import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkflowCircuitComponent } from './workflow-circuit.component';

describe('WorkflowCircuitComponent', () => {
  let component: WorkflowCircuitComponent;
  let fixture: ComponentFixture<WorkflowCircuitComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WorkflowCircuitComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkflowCircuitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
