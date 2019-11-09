import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/_models/Product';
import { ProductService } from 'src/app/_services/product.service';
import { OrderLine } from 'src/app/_models/OrderLine';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Order } from 'src/app/_models/Order';
import { GreaterThanZero } from 'src/app/_validators/CustomValidators';
import { Config } from 'src/app/_models/Config ';

@Component({
  selector: 'app-order-edit',
  templateUrl: './order-edit.component.html',
  styleUrls: ['./order-edit.component.css']
})
export class OrderEditComponent implements OnInit {

  constructor(private productService:ProductService) { }

  productsList: Product[] = [];
  orderLinesList:OrderLine[] = [];
  statusList:string [] = ["Cancelled", "Confirmed", "Pending"];
  paymentMethods:string [] = ["Cash"];

  orderForm: FormGroup;

  ngOnInit() {
    this.initProductsList();  
    this.initOrderForm();
  }

  initProductsList(): void {
    this.productService.getAllProducts().subscribe(
      (response:Product []) =>{
        this.productsList = response;
      },
      (error)=>{
        console.log(error);
      }
    );
  }

  initOrderForm(): void {
    this.orderForm = new FormGroup({
      'orderDate': new FormControl(null, Validators.required),
      'orderSubtotal': new FormControl(null,[Validators.required, GreaterThanZero]),
      'orderStatus': new FormControl(null, Validators.required),
      'orderTax': new FormControl(null, Validators.required),
      'orderPaymentMethod': new FormControl(null, Validators.required),
      'orderTotalPrice': new FormControl(null, Validators.required)
    });
  }

  addNewOrderLine():void {
    this.orderLinesList.push(new OrderLine());
  }

  getPrice(productName: string): number{
    for(let product of this.productsList){
      if(product.name == productName){
        return product.price;
      }
    }
    return null;
  }

  onChange(event, orderLineIndex:number){
    let str:string = event.target.value;
    let splitted:string[] = str.split(",", 2);
    let productName:string = splitted[0];
    let productPrice:number = this.getPrice(productName);
    let ol:OrderLine = this.orderLinesList[orderLineIndex];
    ol.price = productPrice;
    ol.totalPrice = ol.quantity * ol.price;
    this.updateOtherFields();
  }

  onQuantityChange(event, orderLineIndex:number):void {
    let ol:OrderLine = this.orderLinesList[orderLineIndex];
    let quantity:number = event.target.value;
    ol.quantity = quantity;
    if(ol.price){
      ol.totalPrice = ol.price * ol.quantity;
    }
    this.updateOtherFields();
  }

  deleteOrderLine(orderLineIndex:number):void {
    this.orderLinesList.splice(orderLineIndex, 1);
    this.updateOtherFields();
  }

  submitOrderForm() {
    let order:Order = new Order();
    order.deliveryDate = new Date(this.orderForm.value.orderDate).getTime(); //time stamp
    order.subtotal = this.orderForm.value.orderSubtotal;
    order.status = this.orderForm.value.orderStatus;
    order.paymentMethod = this.orderForm.value.orderPaymentMethod;
    order.tax = this.orderForm.value.orderTax;
    order.totalPrice = this.orderForm.value.orderTotalPrice;
    console.log(order);
  }

  updateOtherFields():void {
    let subTotal:number = 0;
    let taxVal:number = 0;
    let totalPrice:number = 0;
    for(let ol of this.orderLinesList){
      if(ol.totalPrice){
        subTotal = subTotal + ol.totalPrice;
      }
    }
    taxVal = subTotal * new Config().tax;
    totalPrice = subTotal + taxVal;
    this.orderForm.patchValue({orderSubtotal: subTotal});
    this.orderForm.patchValue({orderTax: taxVal});
    this.orderForm.patchValue({orderTotalPrice: totalPrice});
  }

}
