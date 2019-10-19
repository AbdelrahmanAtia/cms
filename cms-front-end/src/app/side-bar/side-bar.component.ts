import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }

  viewProducts(): void {
    this.router.navigate(['products', ' ', 0, 1]);
  }

  viewCategories(): void {
    this.router.navigate(['categories']);
  }

  viewOrders(): void {
    this.router.navigate(['orders']);
  }

}