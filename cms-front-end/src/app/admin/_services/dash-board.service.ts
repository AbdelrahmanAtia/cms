import { Injectable } from '@angular/core';
import { DashBoardInfo } from '../_models/DashBoardInfo';
import { Observable } from 'rxjs';
import { Config } from '../_models/Config ';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DashBoardService {

  baseUrl: string = new Config().baseUrl;

  constructor(private httpClient: HttpClient) { }

  getDashboardInfo(): Observable<DashBoardInfo> {
    let url: string = this.baseUrl + "/dashboard/dashboardInfo";
    return this.httpClient.get<DashBoardInfo>(url);
  }

}
