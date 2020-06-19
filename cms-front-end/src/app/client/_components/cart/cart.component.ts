import { Component, OnInit } from '@angular/core';
import { CartItem } from '../../_models/CartItem';
import { CartService } from '../../_services/cart.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CustomValidator } from 'src/app/admin/_validators/CustomValidator';
import { HttpEvent } from '@angular/common/http';

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
  
  constructor(private cartService:CartService) { }

  ngOnInit() { 
    
    this.cartItemList = this.cartService.getItems();
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
    console.log("order submitted");
    console.log(this.orderForm);
  }

  onQuantityChange(event, productId:number){
    let newQuantity:number = event.target.value;
    this.cartItemList.forEach((item:CartItem)=>{
      if(item.productId == productId){
        item.productQuantity = newQuantity;
      }
    });    
  }

}
