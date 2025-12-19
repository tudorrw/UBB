import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityReportsAdminComponent } from './activity-reports-admin.component';

describe('ActivityReportsAdminComponent', () => {
  let component: ActivityReportsAdminComponent;
  let fixture: ComponentFixture<ActivityReportsAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ActivityReportsAdminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ActivityReportsAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
