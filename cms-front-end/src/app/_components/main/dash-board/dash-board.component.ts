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
  ordersToBeDeliver: Order[] = [];
  ordersReceivedToday: Order[] = [];


  now:Date = new Date();
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

  private setDayName():void {
    let now: Date = new Date();
    let days:string[] = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    console.log(now.getDay())
    this.dayName = days[now.getDay()];
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
