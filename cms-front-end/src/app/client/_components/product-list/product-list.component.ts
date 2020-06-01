import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from 'src/app/admin/_services/product.service';
import { HttpResponse } from '@angular/common/http';
import { Product } from 'src/app/admin/_models/Product';
import { Config } from 'src/app/admin/_models/Config ';
import { Cart } from '../../_models/Cart';
import { CartItem } from '../../_models/CartItem';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  categoryId: number;
  searchTerm: string = '';
  pageNumber: number = 1;
  products: Product[] = [];
  imageBaseUrl: string = new Config().baseUrl + "/products/getImage";

  constructor(private activatedRoute: ActivatedRoute,
    private productService: ProductService) { }

  ngOnInit() {
    this.listenToRouteParamChanges();
  }

  // called when any route param changes
  listenToRouteParamChanges() {
    this.activatedRoute.params.subscribe(
      params => {
        this.categoryId = +params['categoryId'];
        this.initializeProductsList();
      });
  }

  initializeProductsList(): void {
    this.productService.getProducts(this.searchTerm, this.categoryId, this.pageNumber).subscribe(
      (response: HttpResponse<Product[]>) => {
        //this.totalPages = +response.headers.get('totalPages');
        this.products = response.body;
        console.log(this.products);
      },
      (error) => { console.log(error) }
    );
  }

  addToCart(productId: number, productName: string, productPrice): void {

    let qty: number = +(document.getElementById(productId.toString()).getAttribute('value'));

    let itemExist:boolean = false;
    Cart.cartItemList.forEach(item => {
      if (item.productId == productId) {
        item.productQuantity = qty;
        itemExist = true;
        return;
      }
    });

    if(!itemExist){
      let cartItem: CartItem = new CartItem();
      cartItem.productId = productId;
      cartItem.productName = productName;
      cartItem.productPrice = productPrice;
      cartItem.productQuantity = qty;
      Cart.cartItemList.push(cartItem);
    }
    
    console.log(Cart.cartItemList);
  }

  onQuantityChange(event, productId: number): void {
    let qty: number = event.target.value;
    document.getElementById(productId.toString()).setAttribute("value", qty.toString());
  }

}
