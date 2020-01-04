import { Injectable } from '@angular/core';
import { Config } from '../_models/Config ';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { User } from '../_models/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  baseUrl: string = new Config().baseUrl;

  constructor(private http: HttpClient) { }

  getAllUsers(): Observable<User[]> {
    let url: string = this.baseUrl + "/users/all";
    return this.http.get<User[]>(url);
  }

  getUser(userId: number): Observable<User> {
    let url: string = this.baseUrl + "/users/" + userId;
    return this.http.get<User>(url);
  }

  addNewUser(user: User): Observable<User> {
    let url: string = this.baseUrl + "/users";
    return this.http.post<User>(url, user);
  }

  updateUser(user: User): Observable<User> {
    let url: string = this.baseUrl + "/users";
    return this.http.put<User>(url, user);
  }
}
