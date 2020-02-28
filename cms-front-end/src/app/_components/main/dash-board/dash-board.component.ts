import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DashBoardInfo } from 'src/app/_models/DashBoardInfo';
import { DashBoardService } from 'src/app/_services/dash-board.service';
import { OrderService } from 'src/app/_services/order.service';
import { Order } from 'src/app/_models/Order';

@Component({
  selector: 'app-dash-board',
  templateUrl: './dash-board.component.html',
  styleUrls: ['./dash-board.component.css']
})
export class DashBoardComponent implements OnInit {

  dashBoardInfo: DashBoardInfo;

  ordersToBeDeliveredNext: Order[] = [];


  constructor(private dashBoardService: DashBoardService,
    private orderService: OrderService,
    private router: Router) { }

  ngOnInit() {
    this.initializeDashBoardInfo();
    this.initializeOrdersToBeDeliveredNext();
  }

  private initializeDashBoardInfo() {
    this.dashBoardService.getDashboardInfo().subscribe(
      (response) => {
        this.dashBoardInfo = response;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  private initializeOrdersToBeDeliveredNext() {
    this.orderService.getNextOrdersToBeDelivered().subscribe(
      (response) => {
        this.ordersToBeDeliveredNext = response;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  addOrder(): void {
    this.router.navigate(['main', 'orders', 'new']);
  }

  addProduct(): void {
    this.router.navigate(['main', 'products', 'new']);
  }

  viewOrders(): void {
    this.router.navigate(['main', 'orders']);
  }

  viewProducts(): void {
    this.router.navigate(['main', 'products', ' ', 0, 1]);
  }

}
