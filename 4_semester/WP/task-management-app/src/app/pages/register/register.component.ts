import { Component, OnInit } from '@angular/core';
import { FormControl, FormControlName, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {
  hide: boolean = true;
  hideConfirmedPassword: boolean = true;
  registerForm!: FormGroup;


  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.registerForm = new FormGroup({
      name: new FormControl('', Validators.required),
      username: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required),
      confirmedPassword: new FormControl('', Validators.required)
    });
  }

  register() {
    console.log(this.registerForm.value);
    this.authService.register(this.registerForm.value.username, this.registerForm.value.password).then((res: any) => {
      console.log(res);
    }).catch((error: any) => {
      console.log(error);
    });
  }
}
