import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskPopupComponent } from './task-popup.component';

describe('TaskPopupComponent', () => {
  let component: TaskPopupComponent;
  let fixture: ComponentFixture<TaskPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TaskPopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TaskPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
