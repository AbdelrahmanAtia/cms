import { Injectable } from '@angular/core';
import { Product } from '../_models/Product';
import { Observable } from 'rxjs';
import { Config } from '../_models/Config ';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  baseUrl: string = new Config().baseUrl;
  
  constructor(private http: HttpClient) { }

  getProducts(): Observable<Product[]> {
    let url: string = this.baseUrl + "/products";
    return this.http.get<Product[]>(url);
  }


}
