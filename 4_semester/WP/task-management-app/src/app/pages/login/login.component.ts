import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  hide: boolean = true;
  loginForm!: FormGroup;

  constructor(
    private authService: AuthService,
    public dialog: MatDialog,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required),
    });
    if (this.authService.isLoggedIn()) {
      if (typeof window !== 'undefined') {
        const token = localStorage.getItem('token');
        if (token) {
          this.router.navigate(['/dashboard']);
        }
      }
    }
  }

  loginWithGoogle() {

    this.authService.signInWithGoogle().then((res: any) => {
    }).catch((error: any) => {
    });
  }

  loginWithEmailAndPassword() {
    console.log(this.loginForm.value);
    this.authService.login(this.loginForm.value.username, this.loginForm.value.password).then((res: any) => {
    }).catch((error: any) => {
      console.log("num merge");

    });
  }

}
