import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Config } from 'src/app/admin/_models/Config ';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  selectedNav:string = 'menu';

  constructor(private router:Router) { }

  ngOnInit() {
  }

  viewCategories():void{
    this.selectedNav = 'menu';
    this.router.navigate(Config.clientCategoriesRoute);
  }

  viewCart():void {
    this.selectedNav = 'cart';
    this.router.navigate(Config.clientCartRoute);
  }

}
