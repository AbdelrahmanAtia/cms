import { Component, OnInit } from '@angular/core';
import { CartService } from '../../_services/cart.service';
import { CartItem } from '../../_models/CartItem';
import { Order } from 'src/app/admin/_models/Order';
import { OrderService } from 'src/app/admin/_services/order.service';
import { Router } from '@angular/router';
import { Config } from 'src/app/admin/_models/Config ';

@Component({
  selector: 'app-order-preview',
  templateUrl: './order-preview.component.html',
  styleUrls: ['./order-preview.component.css']
})
export class OrderPreviewComponent implements OnInit {

  cartItemList: CartItem[] = [];
  order:Order;
  orderSubmitted: boolean = false;

  constructor(private cartService:CartService,
              private orderService:OrderService,
              private router:Router) { }

  ngOnInit() {
    this.order = this.cartService.order;
    this.cartItemList = this.cartService.getItems();
  }

  submitOrder(){
    this.orderService.addNewOrder(this.order).subscribe(
      (response: Order) => {
        this.orderSubmitted = true;
        this.order = undefined;
        this.cartService.cartItemList = [];
      }
      , (error) => console.log(error)
    );
  }

  cancelOrder(){
    this.router.navigate(Config.clientCartRoute);
  }

  startOver(){
    //navigate to home page..
    this.router.navigate(Config.clientCategoriesRoute);
  }

}