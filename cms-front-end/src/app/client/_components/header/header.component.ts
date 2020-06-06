import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Config } from 'src/app/admin/_models/Config ';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit() {
  }

  viewCategories():void{
    this.router.navigate(Config.clientCategoriesRoute);
  }

  viewCart():void {
    this.router.navigate(Config.clientCartRoute);
  }

}
