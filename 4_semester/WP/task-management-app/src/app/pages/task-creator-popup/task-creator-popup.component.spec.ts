import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskCreatorPopupComponent } from './task-creator-popup.component';

describe('TaskCreatorPopupComponent', () => {
  let component: TaskCreatorPopupComponent;
  let fixture: ComponentFixture<TaskCreatorPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TaskCreatorPopupComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(TaskCreatorPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
