import { Injectable } from '@angular/core';
import { Config } from '../_models/Config ';
import { HttpClient } from '@angular/common/http';
import { Profile } from '../_models/Profile';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  baseUrl: string = new Config().baseUrl;

  constructor(private httpClient: HttpClient) { }

  getCurrentUserProfile(): Observable<Profile> {
    let url: string = this.baseUrl + "/profile";
    return this.httpClient.get<Profile>(url);
  }

  updateCurrentUserProfile(profile: Profile): Observable<Profile> {
    let url: string = this.baseUrl + "/profile";
    return this.httpClient.put<Profile>(url, profile);
  }


}
