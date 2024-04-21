import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GoogleLoginCardComponent } from './google-login-card.component';

describe('GoogleLoginCardComponent', () => {
  let component: GoogleLoginCardComponent;
  let fixture: ComponentFixture<GoogleLoginCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GoogleLoginCardComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(GoogleLoginCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
