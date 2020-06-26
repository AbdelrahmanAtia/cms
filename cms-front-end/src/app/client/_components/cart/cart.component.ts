import { Component, OnInit } from '@angular/core';
import { CartItem } from '../../_models/CartItem';
import { CartService } from '../../_services/cart.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CustomValidator } from 'src/app/admin/_validators/CustomValidator';
import { Order } from 'src/app/admin/_models/Order';
import { Client } from 'src/app/admin/_models/Client';
import { OrderLine } from 'src/app/admin/_models/OrderLine';
import { OrderService } from 'src/app/admin/_services/order.service';
import { Router } from '@angular/router';
import { Config } from 'src/app/admin/_models/Config ';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
 
  cartItemList: CartItem[] = [];
  orderForm:FormGroup;
  minDate: string = this.formattedDate();
  paymentMethods: string[] = ["Cash"];
  cartSubtotal:number;
  
  constructor(private cartService:CartService,
              private orderService:OrderService,
              private router: Router) { }

  ngOnInit() { 
    this.cartItemList = this.cartService.getItems();
    this.cartSubtotal = this.cartService.getSubtotal();
    this.orderForm = new FormGroup(
      {
        'orderDate': new FormControl(null, [Validators.required, CustomValidator.DateAfterNow(false)]),
        'clientName': new FormControl(null, CustomValidator.notBlank),
        'clientEmail': new FormControl(null, [Validators.email, Validators.required]),
        'clientPhone': new FormControl(null, CustomValidator.notBlank),
        'clientCompany': new FormControl(null),
        'clientAddress': new FormControl(null),
        'clientCity': new FormControl(null),
        'clientState': new FormControl(null),
        'clientZip': new FormControl(null),
        'clientSpecialInstructions': new FormControl(null),
        'orderPaymentMethod': new FormControl(null, [Validators.required])
      }
    );
  }

  deleteCartItem(productId:number): void {
    console.log('starting deleteCartItem()')
    this.cartService.deleteCartItem(productId);
    this.cartSubtotal = this.cartService.getSubtotal();
  }

  private formattedDate(): string {
    let current_datetime = new Date();
    let year: number = current_datetime.getFullYear();

    let month: string = (current_datetime.getMonth() + 1).toString();
    if (month.length == 1) {
      month = '0' + month;
    }

    let day: string = current_datetime.getDate().toString();
    if (day.length == 1) {
      day = '0' + day;
    }

    let hours: string = current_datetime.getHours().toString();
    if (hours.length == 1) {
      hours = '0' + hours;
    }

    let minutes: string = current_datetime.getMinutes().toString();
    if (minutes.length == 1) {
      minutes = '0' + minutes;
    }

    let formattedDateTime = year + "-" + month + "-" + day + 'T' + hours + ':' + minutes;
    return formattedDateTime;
  }

  submitOrderForm() {
    let order: Order = new Order();
    let client: Client = new Client();

    order.subtotal = this.cartSubtotal;
    order.tax = this.orderForm.value.orderTax;
    //order.totalPrice = this.orderForm.value.orderTotalPrice;

    order.deliveryDate = new Date(this.orderForm.value.orderDate).getTime(); //time stamp
    order.orderStatus = "PENDING";
    order.paymentMethod = this.orderForm.value.orderPaymentMethod;

    client.name = this.orderForm.value.clientName;
    client.email = this.orderForm.value.clientEmail;
    client.phone = this.orderForm.value.clientPhone;
    client.company = this.orderForm.value.clientCompany;
    client.address = this.orderForm.value.clientAddress;
    client.city = this.orderForm.value.clientCity;
    client.state = this.orderForm.value.clientState;
    client.zip = this.orderForm.value.clientZip;
    client.specialInstructions = this.orderForm.value.clientSpecialInstructions;

    order.client = client;

    for (let cartItem of this.cartItemList) {
        let orderLine:OrderLine = new OrderLine();
        orderLine.price = cartItem.productPrice;
        orderLine.product.id = cartItem.productId;
        orderLine.quantity = cartItem.productQuantity;
        order.orderLines.push(orderLine);
    }
 
    this.cartService.order = order;
    
    //navigate to order preview component..
    this.router.navigate(Config.clientOrderPreviewRoute);

  }

  onQuantityChange(event, productId:number){
    let newQuantity:number = event.target.value;
    this.cartItemList.forEach((item:CartItem)=>{
      if(item.productId == productId){
        item.productQuantity = newQuantity;
      }
    });
    //update sub total value
    this.cartSubtotal = this.cartService.getSubtotal();
  }

}
