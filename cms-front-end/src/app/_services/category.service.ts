import { Injectable } from '@angular/core';
import { Category } from '../_models/Category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {


  categories: Category[] = [];

  c1: Category = {
    id: 1,
    name: "category1Name",
    description: "category1Desc"
  }

  c2: Category = {
    id: 2,
    name: "category2Name",
    description: "category2Desc"
  }
  constructor() {
    this.categories.push(this.c1);
    this.categories.push(this.c2);
  }

  getCategories(): Category[] {
    return this.categories;
  }
}
