import { Component, OnInit } from '@angular/core';
import { CartItem } from '../../_models/CartItem';
import { CartService } from '../../_services/cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {


  cartItemList: CartItem[] = [];
  
  constructor(private cartService:CartService) { }

  ngOnInit() { 
    this.cartItemList = this.cartService.getItems();
  }

}
