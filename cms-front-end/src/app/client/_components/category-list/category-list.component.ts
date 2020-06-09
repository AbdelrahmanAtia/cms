import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CategoryService } from 'src/app/admin/_services/category.service';
import { Category } from 'src/app/admin/_models/Category';
import { Config } from 'src/app/admin/_models/Config ';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {

  categoryList: Category[] = [];
  imageBaseUrl: string = new Config().baseUrl + "/categories/getImage";


  constructor(private router: Router,
    private categoryService: CategoryService) { }

  ngOnInit() {
    this.initializeCategoriesList();
  }

  initializeCategoriesList() {

    this.categoryService.getAllCategories().subscribe(
      (response: Category[]) => {
        this.categoryList = response;
      },
      (error) => { console.log(error) }
    );
  }

  viewCategoryProducts(categoryId: number): void {
    this.router.navigate(Config.clientProductsRoute(categoryId));
  }


}
