import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityReportsComponent } from './activity-reports.component';

describe('ActivityReportsComponent', () => {
  let component: ActivityReportsComponent;
  let fixture: ComponentFixture<ActivityReportsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ActivityReportsComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ActivityReportsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
