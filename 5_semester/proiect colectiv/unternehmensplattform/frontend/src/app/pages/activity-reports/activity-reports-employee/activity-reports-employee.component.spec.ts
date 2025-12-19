import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityReportsEmployeeComponent } from './activity-reports-employee.component';

describe('ActivityReportsEmployeeComponent', () => {
  let component: ActivityReportsEmployeeComponent;
  let fixture: ComponentFixture<ActivityReportsEmployeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ActivityReportsEmployeeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ActivityReportsEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
