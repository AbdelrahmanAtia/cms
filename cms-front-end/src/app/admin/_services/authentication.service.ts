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

  login(user:User):Observable<User> {
    let url: string = this.baseUrl + "/authentication/login";
    return this.http.post<User>(url, user);
  }

  // this method is called after login to set auth token and other info in local storage
  setAuthToken(user:User){
    localStorage.setItem('authToken', window.btoa(user.name + ':' + user.password));
    localStorage.setItem('currentUserAuthority' , user.authority.name);
    localStorage.setItem('currentUserId', user.id + '');
  }


  public getAuthToken():string {
    return localStorage.getItem('authToken');
  }
  
 
  public getCurrentUserAuthority():string {
    return localStorage.getItem('currentUserAuthority');
  }

  public getCurrentUserId():number {
    return +(localStorage.getItem('currentUserId'));
  }
  
  //called after updating user profile info
  updateAuthenticationToken(username:string, password:string):void {
    localStorage.setItem('authToken', window.btoa(username + ':' + password));
  }
  
  logout():void {
      // remove user info from local storage to log user out
      localStorage.removeItem('authToken');
      localStorage.removeItem('currentUserAuthority');
      localStorage.removeItem('currentUserId');
  }

}
