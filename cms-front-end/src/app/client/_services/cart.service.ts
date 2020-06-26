import { Injectable } from '@angular/core';
import { CartItem } from '../_models/CartItem';
import { Order } from 'src/app/admin/_models/Order';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  cartItemList: CartItem[] = [];
  order:Order;

  constructor() {

    /*
    let cartItem = new CartItem();
    cartItem.productName = 'Croissants';
    cartItem.productPrice = 1.2;
    cartItem.productQuantity = 3;
    cartItem.totalPrice = 3.33;

    let cartItem2 = new CartItem();
    cartItem2.productName = 'Palmiers';
    cartItem2.productPrice = 0.9;
    cartItem2.productQuantity = 5;
    cartItem2.totalPrice = 4.5;

    this.cartItemList.push(cartItem);
    this.cartItemList.push(cartItem2);
    */

  }

  getItems(): CartItem[] {
    return this.cartItemList;
  }

  addToCart(cartItem: CartItem): void {
    let itemExist: boolean = false;
    this.cartItemList.forEach(item => {
      if (item.productId == cartItem.productId) {
        item.productQuantity = cartItem.productQuantity;
        itemExist = true;
        return;
      }
    });

    if (!itemExist) {
      this.cartItemList.push(cartItem);
    }
  }

  deleteCartItem(productId: number) {
    this.cartItemList.forEach((item, index) => {
      console.log("index = " + index);
      if (item.productId == productId) {
        this.cartItemList.splice(index, 1);
        return;
      }
    });
  }

  getProductQuantity(productId: number): number {
    for (let item of this.cartItemList) {
      if (item.productId == productId) {
        return item.productQuantity;
      }
    }
    return 0;
  }

  getSubtotal(): number {
    console.log("starting getSubtotal()")
    let sum: number = 0;
    this.cartItemList.forEach((item: CartItem) => {
      sum = sum + item.productQuantity * item.productPrice;
    });
    console.log('sum = ' + sum);
    return sum;
  }

}
