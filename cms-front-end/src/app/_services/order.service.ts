import { Injectable } from '@angular/core';
import { Config } from '../_models/Config ';
import { Observable } from 'rxjs';
import { Order } from '../_models/Order';
import { HttpClient } from '@angular/common/http';
import { Response } from '../_models/Response';

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

  getOrder(orderId:number):Observable<Order>{
    let url: string = this.baseUrl + "/orders/" + orderId;
    return this.http.get<Order>(url);
  }

  addNewOrder(order: Order): Observable<Order> {
    let url: string = this.baseUrl + "/orders";
    return this.http.post<Order>(url, order);
  }

  updateOrder(order: Order): Observable<Order> {
    let url: string = this.baseUrl + "/orders";
    return this.http.put<Order>(url, order);
  }

  deleteOrder(orderId: number): Observable<Response> {
    let url: string = this.baseUrl + "/orders/" + orderId;
    return this.http.delete<Response>(url);
  }

  getTotalOrdersCount(): Observable<number>{
    let url: string = this.baseUrl + "/orders/count";
    return this.http.get<number>(url);
  }

  getNextOrdersToBeDelivered(): Observable<Order[]>{
    let url: string = this.baseUrl + "/orders/nextToDeliver";
    return this.http.get<Order[]>(url);
  }


}
