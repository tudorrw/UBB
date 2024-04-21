import { Injectable } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { Router } from '@angular/router';
import { GoogleAuthProvider } from 'firebase/auth'
import { MatSnackBar } from '@angular/material/snack-bar';
import { error } from 'console';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private afs: AngularFireAuth,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }
  isLoggedIn(): boolean {
    if (typeof window !== 'undefined') {
      return !!localStorage.getItem('token');
    }
    return false;
  }

  signInWithGoogle() {
    return this.afs.signInWithPopup(new GoogleAuthProvider).then(res => {
      this.router.navigate(['/dashboard']);
      this.openSnackBar('Logged in with Google!');
      console.log(res);

      localStorage.setItem('token', JSON.stringify(res.user?.uid));

    }, err => {
      this.openSnackBar('Cannot sign in with Google!');
    })
  }

  login(username: string, password: string) {
    return this.afs.signInWithEmailAndPassword(username, password).then(res => {
      localStorage.setItem('token', 'true');
      console.log(res);
      if (res.user?.emailVerified == true) {
        this.router.navigate(['dashboard']);
      } else {
        this.router.navigate(['/verify-email']);
      }
    }, err => {
      if (err.code === 'auth/invalid-credential') {
        this.openSnackBar("The supplied auth credential is incorrect, malformed or has expired.");
      }
      // this.openSnackBar('Email is not registered!');
      this.router.navigate(['/login']);
    }).catch((err: any) => {
      console.log("num merge");
    });
  }

  register(username: string, password: string) {
    return this.afs.createUserWithEmailAndPassword(username, password).then(res => {
      this.openSnackBar('User registered successfully!');
      console.log(res.user);
      this.sendEmailForVerification(res.user);
      this.router.navigate(['/login']);

    }, err => {
      console.log(err);
      if (err.code === 'auth/weak-password') {
        this.openSnackBar("Password should be at least 6 characters long!")
      }
      if (err.code === 'auth/email-already-in-use') {
        this.openSnackBar("Email already in use!");
      }
      this.router.navigate(['/register']);
    });
  }

  logout() {
    this.afs.signOut().then(() => {
      localStorage.removeItem('token');
      this.router.navigate(['/login']);
    }, err => {
      this.openSnackBar("Could not log out!");
    })
  }

  forgotPassword(username: string) {
    this.afs.sendPasswordResetEmail(username).then(() => {
      this.router.navigate(['/verify-email']);
    }, err => {
      this.openSnackBar("Something went wrong");
    });
  }

  sendEmailForVerification(user: any) {
    user.sendEmailVerification().then((res: any) => {
      this.router.navigate(['/verify-email']);
    }, (err: any) => {
      this.openSnackBar("Something went wrong. Not able to send verification email.");
    });
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, 'Close', {
      duration: 5000
    });
  }

}
