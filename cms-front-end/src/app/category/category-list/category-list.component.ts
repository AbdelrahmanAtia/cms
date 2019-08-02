import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/_models/Category';
import { CategoryService } from 'src/app/_services/category.service';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {

  categories: Category[] = [];
  constructor(private categoryService: CategoryService) { }

  ngOnInit() {
    this.categoryService.getCategories().subscribe(
      (response: Category[]) => { this.categories = response; },
      (error) => {console.log(error)}
    );
  }

  addNewCategory(): void {

  }

}
