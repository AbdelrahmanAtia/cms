import { Component, OnInit } from '@angular/core';
import { OrderService } from 'src/app/_services/order.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Order } from 'src/app/_models/Order';
import { Response } from 'src/app/_models/Response';

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

  deleteOrder(orderId: number): void {
    
    if (!confirm("Are you sure you want to delete the selected record?")) {
      return;
    }

    this.orderService.deleteOrder(orderId).subscribe(
      (response: Response) => {
        if (response.status = "200") {
          console.log(response.message);
          this.ngOnInit();
        }
        else if (response.status = "404") {
          throw new Error(response.message);
        }
      },
      (error) => { console.log(error); }
    );
  }
  
}
