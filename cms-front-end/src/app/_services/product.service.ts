import { Injectable } from '@angular/core';
import { Product } from '../_models/Product';
import { Observable } from 'rxjs';
import { Config } from '../_models/Config ';
import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Response } from 'src/app/_models/Response';
@Injectable({
  providedIn: 'root'
})
export class ProductService {

  baseUrl: string = new Config().baseUrl;

  constructor(private http: HttpClient) { }

  getAllProducts(): Observable<Product[]> {
    let url: string = this.baseUrl + "/products/all";
    return this.http.get<Product[]>(url);
  }

  getProducts(searchTerm:string, categoryId: number, pageNumber: number): Observable<HttpResponse<Product[]>> {
    pageNumber--; // cause pageNumber starts at zero not 1 according to backend..
    let url: string = this.baseUrl + "/products";

    return this.http.get<any>(url, {
      params: {
        searchTerm: searchTerm,
        categoryId: categoryId.toString(),
        pageNumber: pageNumber.toString(),
        pageSize: new Config().pageSize.toString()
      }
      , observe: 'response'
    });
  }

  getProduct(productId: number): Observable<Product> {
    let url: string = this.baseUrl + "/products/" + productId;
    return this.http.get<Product>(url);
  }

  addNewProduct(product: Product): Observable<Product> {
    let url: string = this.baseUrl + "/products";
    return this.http.post<Product>(url, product);
  }

  updateProduct(product: Product): Observable<Product> {
    let url: string = this.baseUrl + "/products";
    return this.http.put<Product>(url, product);
  }

  deleteProduct(productId: number): Observable<Response> {
    let url: string = this.baseUrl + "/products/" + productId;
    return this.http.delete<Response>(url);
  }

  searchForProductByName(searchTerm: string): Observable<Product[]> {
    let url: string = this.baseUrl + "/products?searchTerm=" + searchTerm;
    return this.http.get<Product[]>(url);
  }

}
