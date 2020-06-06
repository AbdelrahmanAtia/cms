import { Injectable } from '@angular/core';
import { CartItem } from '../_models/CartItem';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  cartItemList: CartItem[] = [];

  constructor() {

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

  getItems(): CartItem[] {
    return this.cartItemList;
  }


}
