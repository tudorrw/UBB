import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  set token(token: string) {
    localStorage.setItem('token', token);
  }

  get token() {
    return localStorage.getItem('token') as string;
  }
  isTokenNotValid() {
    return !this.isTokenValid();
  }

  private isTokenValid() {
    const token = this.token;
    if(!token) {
      return false;
    }
    //decode the token
    const jwtHelper = new JwtHelperService();
    //check expiry date
    const isTokenExpired = jwtHelper.isTokenExpired(token);
    console.log("token expired", isTokenExpired);

    const payload = JSON.parse(atob(token.split('.')[1]));
    const expiryDate = new Date(payload.exp * 1000); // Convert seconds to milliseconds

    console.log(`Token expires on: ${expiryDate.toLocaleString('ro-RO', { timeZone: 'Europe/Bucharest' })}`);
    if(isTokenExpired) {
      localStorage.clear();
      return false
    }
    return true;
  }

  get userRoles(): string {
    const token = this.token;
    if (token) {
      const jwtHelper = new JwtHelperService();
      const decodedToken = jwtHelper.decodeToken(token);
      return decodedToken.authorities[0];
    }
    return "";
  }

}
