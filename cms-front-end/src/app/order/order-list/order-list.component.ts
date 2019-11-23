import { Component, OnInit } from '@angular/core';
import { OrderService } from 'src/app/_services/order.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Order } from 'src/app/_models/Order';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {

  orders: Order[] = [];

  constructor(private orderService: OrderService,
              private router: Router,
              private route: ActivatedRoute) { }


  ngOnInit() {
    this.orderService.getOrders().subscribe(
      (response: Order[]) => { this.orders = response; },
      (error) => { console.log(error) }
    );
  }

  addNewOrder(): void {
    this.router.navigate(['orders', 'new']);
  }

  editOrder(orderId: number): void {
    this.router.navigate(['orders', orderId, 'edit']);
  }
  
}
