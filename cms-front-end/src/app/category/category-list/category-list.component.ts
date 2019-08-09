import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/_models/Category';
import { CategoryService } from 'src/app/_services/category.service';
import { Router } from '@angular/router';
import { Response } from 'src/app/_models/Response';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {

  categories: Category[] = [];
  constructor(private categoryService: CategoryService, private router: Router) { }

  ngOnInit() {
    this.categoryService.getCategories().subscribe(
      (response: Category[]) => { this.categories = response; },
      (error) => { console.log(error) }
    );
  }

  addNewCategory(): void {
    this.router.navigate(['categories', 'new']);
  }

  deleteCategory(categoryId: number): void {
    if(!confirm("Are you sure you want to delete selected record?")){
      return;
    }
    this.categoryService.deleteCategory(categoryId).subscribe(
      (response: Response) => {
        if (response.status = "200") {
          console.log(response.message);
          this.ngOnInit();
        }
        else if (response.status = "404") {
          throw new Error(response.message);
        }
      },
      (error) => { console.log(error); }
    );
  }

}
