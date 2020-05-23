import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/admin/_services/authentication.service';
import { Config } from 'src/app/admin/_models/Config ';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent implements OnInit {

 
  userAuthority:string;

  constructor(private router: Router,
              private authenticationService:AuthenticationService) { }

  ngOnInit() {
    this.userAuthority = localStorage.getItem('currentUserAuthority');
  }

  viewDashboard(): void {
    this.router.navigate(Config.dashBoardRoute);
  }

  viewOrders(): void {
    this.router.navigate(Config.orderListRoute);
  }

  viewProducts(): void {
    this.router.navigate(Config.productListRoute);
  }

  viewCategories(): void {
    this.router.navigate(Config.categoryListRoute);
  }

  viewUsers(): void {
    this.router.navigate(Config.userListRoute);
  }

  viewUserProfile(): void {
    this.router.navigate(Config.profileRoute);
  }

  logout(): void {
    this.authenticationService.logout();
    this.router.navigate(Config.loginRoute);
  }

}