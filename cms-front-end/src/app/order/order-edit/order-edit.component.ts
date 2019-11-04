import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/_models/Product';
import { ProductService } from 'src/app/_services/product.service';
import { OrderLine } from 'src/app/_models/OrderLine';

@Component({
  selector: 'app-order-edit',
  templateUrl: './order-edit.component.html',
  styleUrls: ['./order-edit.component.css']
})
export class OrderEditComponent implements OnInit {

  constructor(private productService:ProductService) { }

  productsList: Product[] = [];
  orderLinesList:OrderLine[] = [];

  ngOnInit() {
    this.initProductsList();    
  }

  initProductsList(): void {
    this.productService.getAllProducts().subscribe(
      (response:Product []) =>{
        this.productsList = response;
        console.log(response);
      },
      (error)=>{
        console.log(error);
      }
    );
  }

  addNewOrderLine():void {
    this.orderLinesList.push(new OrderLine());
  }

  

}
