import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaveRequestsAdminComponent } from './leave-requests-admin.component';

describe('LeaveRequestsAdminComponent', () => {
  let component: LeaveRequestsAdminComponent;
  let fixture: ComponentFixture<LeaveRequestsAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LeaveRequestsAdminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LeaveRequestsAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
