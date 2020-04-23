import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Response } from '../_models/Response';
import { Config } from '../_models/Config ';
import { User } from '../_models/User';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  baseUrl: string = new Config().baseUrl;
  
  constructor(private http: HttpClient) { }

  public getCurrentUserValue():string {
    return localStorage.getItem('currentUser');
  }

  public getCurrentUserAuthority():string {
    return localStorage.getItem('currentUserAuthority');
  }

  login(user:User):Observable<User> {
    let url: string = this.baseUrl + "/authentication/login";
    return this.http.post<User>(url, user);
  }

  updateAuthenticationTokenAndAuthority(username:string, password:string, authority:string):void {
    localStorage.setItem('currentUser', window.btoa(username + ':' + password));
    localStorage.setItem('currentUserAuthority' , authority);
  }

  updateAuthenticationToken(username:string, password:string):void {
    localStorage.setItem('currentUser', window.btoa(username + ':' + password));
  }

  logout():void {
      // remove user info from local storage to log user out
      localStorage.removeItem('currentUser');
      localStorage.removeItem('currentUserAuthority');
  }

  
}
