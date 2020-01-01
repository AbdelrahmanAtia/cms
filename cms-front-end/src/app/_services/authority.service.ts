import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Config } from '../_models/Config ';
import { Authority } from '../_models/Authority';

@Injectable({
  providedIn: 'root'
})
export class AuthorityService {

  baseUrl: string = new Config().baseUrl;

  constructor(private http: HttpClient) { }

  getAllAuthorities(): Observable<Authority[]> {
    let url: string = this.baseUrl + "/authorities/all";
    return this.http.get<Authority[]>(url);
  }

}
     