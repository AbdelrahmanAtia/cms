import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CategoryService } from 'src/app/admin/_services/category.service';
import { Category } from 'src/app/admin/_models/Category';
import { Config } from 'src/app/admin/_models/Config ';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {

  categoryList:Category[] = [];
  imageBaseUrl:string = new Config().baseUrl + "/categories/getImage";


  constructor(private activatedRoute: ActivatedRoute,
              private categoryService: CategoryService) { }

  ngOnInit() {
    this.initializeCategoriesList();
  }

  initializeCategoriesList() {
      console.log('initializing category list..');

      this.categoryService.getAllCategories().subscribe(
        (response: Category[]) => {
         this.categoryList = response;
         console.log(this.categoryList);
        },
        (error) => { console.log(error) }
      );
  }
   
}
