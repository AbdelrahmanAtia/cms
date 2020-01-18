import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/_models/Product';
import { ProductService } from 'src/app/_services/product.service';
import { OrderLine } from 'src/app/_models/OrderLine';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Order } from 'src/app/_models/Order';
import { GreaterThanZero } from 'src/app/_validators/CustomValidator';
import { Config } from 'src/app/_models/Config ';
import { Client } from 'src/app/_models/Client';
import { OrderService } from 'src/app/_services/order.service';
import { ActivatedRoute, Router } from '@angular/router';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-order-edit',
  templateUrl: './order-edit.component.html',
  styleUrls: ['./order-edit.component.css']
})
export class OrderEditComponent implements OnInit {

  constructor(private productService:ProductService,
              private orderService:OrderService,
              private route: ActivatedRoute,
              private router: Router,
              private datepipe: DatePipe) { }


  editMode: boolean = false;
  orderId: number;
  clientId: number;

  productsList: Product[] = [];
  productsIdsList: number[] = [];
  orderLinesList:OrderLine[] = [];
  statusList:string [] = ["Cancelled", "Confirmed", "Pending"];
  paymentMethods:string [] = ["Cash"];

  orderForm: FormGroup;

  ngOnInit() {
    this.initProductsListAndProductsIdsList();  
    this.initOrderForm(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

    this.orderId = this.route.snapshot.params.id;
    this.editMode = this.orderId != null;


    if (this.editMode) {
      // populate order form in case of edit  mode
      this.orderService.getOrder(this.orderId).subscribe(
        (response: Order) => {
          console.log(response);
          this.orderLinesList = response.orderLines;
          this.clientId = response.client.id;

          this.initOrderForm(this.datepipe.transform(response.deliveryDate, "yyyy-MM-ddTHH:mm"),
                             response.status, 
                             response.paymentMethod, 
                             response.subtotal,
                             response.tax, 
                             response.totalPrice, 
                             response.client.name,
                             response.client.email, 
                             response.client.phone,
                             response.client.company, 
                             response.client.address,
                             response.client.city,
                             response.client.state,
                             response.client.zip,
                             response.client.specialInstructions);

        }, (error) => { console.log(error); }
      );
    }

  }

  initProductsListAndProductsIdsList(): void {
    this.productService.getAllProducts().subscribe(
      (response:Product []) =>{
        this.productsList = response;
        for(let p of response){
          this.productsIdsList.push(p.id);
        }
      },
      (error)=>{
        console.log(error);
      }
    );
  }

  initOrderForm(orderDate:string,
                orderStatus:string,
                orderPaymentMethod:string,
                subtotal:number, 
                tax:number, 
                total:number, 
                clientName:string,
                clientEmail:string, 
                clientPhone:string, 
                clientCompany:string, 
                clientAddress:string,
                clientCity:string,
                clientState:string,
                clientZip:string,
                clientSpecialInstructions:string): void {

    this.orderForm = new FormGroup({
      'orderDate': new FormControl(orderDate, Validators.required),
      'orderSubtotal': new FormControl(subtotal,[Validators.required, GreaterThanZero]),
      'orderStatus': new FormControl(orderStatus, Validators.required),
      'orderTax': new FormControl(tax, Validators.required),
      'orderPaymentMethod': new FormControl(orderPaymentMethod, Validators.required),
      'orderTotalPrice': new FormControl(total, Validators.required),
      'clientName': new FormControl(clientName, Validators.required),
      'clientEmail': new FormControl(clientEmail, Validators.required),
      'clientPhone': new FormControl(clientPhone, Validators.required),
      'clientCompany': new FormControl(clientCompany, null),
      'clientAddress': new FormControl(clientAddress, null),
      'clientCity': new FormControl(clientCity, null),
      'clientState': new FormControl(clientState, null),
      'clientZip': new FormControl(clientZip, null),
      'clientSpecialInstructions': new FormControl(clientSpecialInstructions, null)
    });
  }

  addNewOrderLine():void {
    this.orderLinesList.push(new OrderLine());
  }

  deleteOrderLine(orderLineIndex:number):void {
    this.orderLinesList.splice(orderLineIndex, 1);
    this.updateOtherFields();
  }

  getProduct(productId: number): Product{
    for(let product of this.productsList){
      if(product.id == productId){
        return product;
      }
    }
    return null;
  }

  onChange(event, orderLineIndex:number) {
    let productId:number = event.target.value;
    let product = this.getProduct(productId);
    let productPrice:number = product.price;
    let ol:OrderLine = this.orderLinesList[orderLineIndex];
    //for orderline we need a product with id specified..we don't have to send all the 
    //product data over the network..we need only it's id
    let p:Product = new Product();
    p.id = product.id;
    ol.product = p;
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

 

  submitOrderForm() {
    console.log("starting submitOrderForm()....");
    
    let order:Order = new Order();
    let client:Client = new Client();

    if(this.editMode){
      order.id = this.orderId;
      client.id = this.clientId;
    }
    
    order.deliveryDate = new Date(this.orderForm.value.orderDate).getTime(); //time stamp
    order.subtotal = this.orderForm.value.orderSubtotal;
    order.status = this.orderForm.value.orderStatus;
    order.paymentMethod = this.orderForm.value.orderPaymentMethod;
    order.tax = this.orderForm.value.orderTax;
    order.totalPrice = this.orderForm.value.orderTotalPrice;
    
    
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
    for(let ol of this.orderLinesList){
      if(ol.product.id != undefined){
        order.orderLines.push(ol);
      }
    }

    if(this.editMode){
      this.updateExistingOrder(order);
    } else {
      this.addNewOrder(order);
    }
  }

  addNewOrder(order: Order) {
    console.log(order);
    this.orderService.addNewOrder(order).subscribe(
      (response: Order) => this.router.navigate(['/orders'])
      , (error) => console.log(error)
    );
  }

  updateExistingOrder(order: Order) {
    console.log(order);
    this.orderService.updateOrder(order).subscribe(
      (response: Order) => this.router.navigate(['/orders'])
      , (error) => console.log(error)
    );
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

  cancel(): void {
    this.router.navigate(['/orders']);
  }

}
