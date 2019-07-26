import { Injectable } from '@angular/core';
import { Product } from '../_models/Product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  products: Product[] = [];

  p1:Product = {
    id: 1,
    name: "p1Name",
    description: "p1Desc",
    price: 25,
    active: true,
    imageUrl: "http://www.thegatenewcastle.co.uk/images/tenants/headers/pizza-hut-mobile.jpg"

  }

  p2:Product = {
    id: 2,
    name: "p2Name",
    description: "p2Desc",
    price: 30,
    active: true,
    imageUrl: "http://www.thegatenewcastle.co.uk/images/tenants/headers/pizza-hut-mobile.jpg"
  }

  constructor() { 
    this.products.push(this.p1);
    this.products.push(this.p2);
  }

  getProducts():Product[] {
    return this.products;
  }
}
