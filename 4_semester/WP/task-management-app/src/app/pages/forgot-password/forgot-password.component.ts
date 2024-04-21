import { Component, OnInit } from '@angular/core';
import { FormControl, FormControlName, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.css'
})
export class ForgotPasswordComponent implements OnInit {
  hide: boolean = true;
  hideConfirmedPassword: boolean = true;
  forgotPasswordForm!: FormGroup;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.forgotPasswordForm = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.email])
    });
  }

  forgotPassword() {
    this.authService.forgotPassword(this.forgotPasswordForm.value.username);
  }

}
