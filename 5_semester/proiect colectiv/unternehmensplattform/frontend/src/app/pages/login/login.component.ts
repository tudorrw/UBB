import { Component, OnInit } from '@angular/core';
import {AuthenticationRequest} from "../../services/models/authentication-request";
import { Router } from '@angular/router';
import {AuthenticationControllerService} from "../../services/services/authentication-controller.service";
import {TokenService} from "../../services/token/token.service";
import { Message } from 'primeng/api';  // Import Message type

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit{

  authRequest: AuthenticationRequest = {email: '', passwordHash: ''};
  errorMsg: Message[] = [];  // Change to Message array

  constructor(
    private router: Router,
    private authService: AuthenticationControllerService,
    private tokenService: TokenService
  ) { }

  ngOnInit(): void {
    // if (typeof window !== 'undefined' && window.localStorage) {
    //   const existingToken = localStorage.getItem('token');
    //
    //   if (existingToken) {
    //     if(this.tokenService.isTokenNotValid()) {
    //       localStorage.removeItem('token');
    //     }
    //   }
    // }
  }

  login() {

    console.log(this.authRequest.passwordHash, this.authRequest.email);

    this.errorMsg = [];  // Clear previous error messages

    this.authService.authenticate({
      body: this.authRequest
    }).subscribe({
      next: (result) => {
        if (typeof window !== 'undefined' && window.localStorage) {
          // Set token on client-side only
          this.tokenService.token = result.token as string;
        }
        this.router.navigate(['dashboard']);
      },
      error: (err) => {
        console.log(err.error);

        if (err.error.validationErrors) {
          this.errorMsg = err.error.validationErrors.map((msg: string) => ({severity: 'error', summary: 'Validation Error', detail: msg}));
        } else {
          this.errorMsg.push({severity: 'error', summary: err.error.businessErrorDescription, detail: err.error.errorMsg});
        }
      }
    });
  }
}
