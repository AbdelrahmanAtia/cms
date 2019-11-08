import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/_models/Product';
import { ProductService } from 'src/app/_services/product.service';
import { OrderLine } from 'src/app/_models/OrderLine';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-order-edit',
  templateUrl: './order-edit.component.html',
  styleUrls: ['./order-edit.component.css']
})
export class OrderEditComponent implements OnInit {

  constructor(private productService:ProductService) { }

  productsList: Product[] = [];
  orderLinesList:OrderLine[] = [];
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
      'orderDate': new FormControl(null, null),
      'orderSubtotal': new FormControl(null, null),
      'orderStatus': new FormControl(null, null),
      'orderTax': new FormControl(null, null),
      'orderPaymentMethod': new FormControl(null, null),
      'orderTotalPrice': new FormControl(null, null),
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
  }

  onQuantityChange(event, orderLineIndex:number):void {
    let ol:OrderLine = this.orderLinesList[orderLineIndex];
    let quantity:number = event.target.value;
    ol.quantity = quantity;
    if(ol.price){
      ol.totalPrice = ol.price * ol.quantity;
    }
  }

  deleteOrderLine(orderLineIndex:number):void {
    this.orderLinesList.splice(orderLineIndex, 1);
  }

  

  

}
