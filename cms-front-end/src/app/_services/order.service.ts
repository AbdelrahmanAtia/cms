import { Injectable } from '@angular/core';
import { Config } from '../_models/Config ';
import { Observable } from 'rxjs';
import { Order } from '../_models/Order';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  baseUrl: string = new Config().baseUrl;

  constructor(private http: HttpClient) { }

  getOrders(): Observable<Order[]>{
    let url: string = this.baseUrl + "/orders";
    return this.http.get<Order[]>(url);
  }


}
