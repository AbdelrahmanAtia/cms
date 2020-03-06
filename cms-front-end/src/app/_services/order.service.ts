import { Injectable } from '@angular/core';
import { Config } from '../_models/Config ';
import { Observable } from 'rxjs';
import { Order } from '../_models/Order';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Response } from '../_models/Response';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  baseUrl: string = new Config().baseUrl;

  constructor(private http: HttpClient) { }

  getAllOrders(): Observable<Order[]> {
    let url: string = this.baseUrl + "/orders/all";
    return this.http.get<Order[]>(url);
  }

  getOrders(orderStatus: string): Observable<HttpResponse<Order[]>> {
    let url: string = this.baseUrl + "/orders";
    return this.http.get<any>(url, {
      params: {
        orderStatus: orderStatus
      }
      , observe: 'response'
    });
  }

  getOrderStatusList(): Observable<string[]> {
    let url: string = this.baseUrl + "/orders/orderStatusList";
    return this.http.get<string[]>(url);
  }

  getOrder(orderId: number): Observable<Order> {
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

  getTotalOrdersCount(): Observable<number> {
    let url: string = this.baseUrl + "/orders/count";
    return this.http.get<number>(url);
  }

  getNextOrdersToBeDelivered(): Observable<Order[]> {
    let url: string = this.baseUrl + "/orders/nextToDeliver";
    return this.http.get<Order[]>(url);
  }

  getOrdersReceivedToday(): Observable<Order[]> {
    let url: string = this.baseUrl + "/orders/receivedToday";
    return this.http.get<Order[]>(url);
  }


}
