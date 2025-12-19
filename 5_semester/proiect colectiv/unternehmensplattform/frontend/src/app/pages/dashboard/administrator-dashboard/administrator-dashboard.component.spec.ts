import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministratorDashboardComponent } from './administrator-dashboard.component';

describe('AdministratorDashboardComponent', () => {
  let component: AdministratorDashboardComponent;
  let fixture: ComponentFixture<AdministratorDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AdministratorDashboardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdministratorDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
