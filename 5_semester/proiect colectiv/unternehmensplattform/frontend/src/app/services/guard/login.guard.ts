import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {TokenService} from "../token/token.service";

export const loginGuard: CanActivateFn = (route, state) => {
  const tokenService = inject(TokenService)
  const router = inject(Router)

  if (typeof window !== 'undefined' && window.localStorage) {
    const existingToken = localStorage.getItem('token');

    if (existingToken) {
      if(tokenService.isTokenNotValid()) {
        localStorage.removeItem('token');
        return true;
      }
      else {
        router.navigate(['dashboard']);
        return false;
      }
    }
  }

  return true;
};
