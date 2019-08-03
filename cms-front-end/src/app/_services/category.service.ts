import { Injectable } from '@angular/core';
import { Category } from '../_models/Category';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Config } from '../_models/Config ';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  baseUrl: string = new Config().baseUrl;

  constructor(private http: HttpClient) { }

  getCategories(): Observable<Category[]> {
    let url: string = this.baseUrl + "/categories";
    return this.http.get<Category[]>(url);
  }

  addNewCategory(category:Category):Observable<Category> {
    let url: string = this.baseUrl + "/categories";
    return this.http.post<Category>(url, category);
  }

}
