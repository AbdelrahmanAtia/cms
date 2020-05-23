import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DashBoardInfo } from 'src/app/admin/_models/DashBoardInfo';
import { DashBoardService } from 'src/app/admin/_services/dash-board.service';
import { OrderService } from 'src/app/admin/_services/order.service';
import { Order } from 'src/app/admin/_models/Order';
import { Config } from 'src/app/admin/_models/Config ';

@Component({
  selector: 'app-dash-board',
  templateUrl: './dash-board.component.html',
  styleUrls: ['./dash-board.component.css']
})
export class DashBoardComponent implements OnInit {

  dashBoardInfo: DashBoardInfo;

  ordersToBeDeliveredNext: Order[] = [];
  ordersToBeDeliver: Order[] = [];
  ordersReceivedToday: Order[] = [];


  now: Date = new Date();
  dayName: string = "";

  constructor(private dashBoardService: DashBoardService,
    private orderService: OrderService,
    private router: Router) { }

  ngOnInit() {
    this.initializeDashBoardInfo();
    this.initializeOrdersToBeDeliveredNext();
    this.initializeOrdersReceivedToday();
    this.setDayName();
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

  private initializeOrdersReceivedToday() {
    this.orderService.getOrdersReceivedToday().subscribe(
      (response) => {
        this.ordersReceivedToday = response;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  private setDayName(): void {
    let now: Date = new Date();
    let days: string[] = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    this.dayName = days[now.getDay()];
  }

  addOrder(): void { 
    this.router.navigate(Config.addNewOrderRoute);
  }

  addProduct(): void {
    this.router.navigate(Config.addNewProductRoute);
  }

  viewOrders(): void {
    this.router.navigate(Config.orderListRoute);
  }

  viewProducts(): void {
    this.router.navigate(Config.productListRoute);
  }

  redirectToOrder(orderId: number): void {
    this.router.navigate(['admin/main', 'orders', orderId, 'edit']);
  }

}
