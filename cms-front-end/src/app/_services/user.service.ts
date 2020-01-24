import { Injectable } from '@angular/core';
import { Config } from '../_models/Config ';
import { Observable } from 'rxjs';
import { User } from '../_models/User';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Response } from 'src/app/_models/Response';
import { map } from 'rxjs/operators';
import { ValidationErrors } from '@angular/forms';

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

  getUsers(searchTerm: string, userStatus: string, pageNumber: number): Observable<HttpResponse<User[]>> {
    pageNumber--; // cause pageNumber starts at zero not 1 according to backend..
    let url: string = this.baseUrl + "/users";
    return this.http.get<any>(url, {
      params: {
        searchTerm: searchTerm,
        userStatus: userStatus,
        pageNumber: pageNumber.toString(),
        pageSize: new Config().pageSize.toString()
      }
      , observe: 'response'
    });
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

  deleteUser(userId: number): Observable<Response> {
    let url: string = this.baseUrl + "/users/" + userId;
    return this.http.delete<Response>(url);
  }

  isUniqueEmail(email: string, userId: number): Observable<ValidationErrors> {
    let url: string = this.baseUrl + "/users/isEmailExist/" + email + "/" + userId;
    return this.http.get<boolean>(url).pipe(
      map((response: boolean) => {
        return response ? null : { "uniqueEmail": true };
      })
    );
  }

}
