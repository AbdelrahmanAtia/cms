import { Component, OnInit } from '@angular/core';
import { CartItem } from '../../_models/CartItem';
import { CartService } from '../../_services/cart.service';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  cartItemList: CartItem[] = [];

  orderForm:FormGroup;
  
  constructor(private cartService:CartService) { }

  ngOnInit() { 
    this.cartItemList = this.cartService.getItems();
    this.orderForm = new FormGroup(
      {
        'orderDate': new FormControl()

      }
    );
  }

  deleteCartItem(productId:number): void {
    console.log('starting deleteCartItem()')
    this.cartService.deleteCartItem(productId);
  }

}
