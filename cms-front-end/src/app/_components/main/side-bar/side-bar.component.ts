import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/_services/authentication.service';

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
    this.router.navigate(['main', 'dashboard']);
  }

  viewOrders(): void {
    this.router.navigate(['main', 'orders', 'ALL', '1']);
  }

  viewProducts(): void {
    this.router.navigate(['main', 'products', ' ', 0, 1]);
  }

  viewCategories(): void {
    this.router.navigate(['main', 'categories', ' ', 1]);
  }

  viewUsers(): void {
    this.router.navigate(['main', 'users', ' ', "All", 1]);
  }

  viewUserProfile(): void {
    this.router.navigate(['main', 'profile']);
  }

  logout(): void {
    this.authenticationService.logout();
    this.router.navigate(['login']);
  }


}