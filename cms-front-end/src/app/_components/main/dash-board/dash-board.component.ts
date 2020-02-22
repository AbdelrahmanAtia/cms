import { Component, OnInit } from '@angular/core';
import { OrderService } from 'src/app/_services/order.service';

@Component({
  selector: 'app-dash-board',
  templateUrl: './dash-board.component.html',
  styleUrls: ['./dash-board.component.css']
})
export class DashBoardComponent implements OnInit {

  totalOrdersCount: number;

  constructor(private orderService: OrderService) { }

  ngOnInit() {
    this.initializeTotalOrdersCount();
  }

  private initializeTotalOrdersCount() {
    this.orderService.getTotalOrdersCount().subscribe(
      (response) => {
        console.log(response);
        this.totalOrdersCount = response;
      },
      (error) => {
        console.log(error);
      }
    );
  }




}
