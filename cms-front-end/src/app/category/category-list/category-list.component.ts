import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/_models/Category';
import { CategoryService } from 'src/app/_services/category.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Response } from 'src/app/_models/Response';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {

  searchTerm: string;
  pageNumber: number;
  totalPages: number;
  categories: Category[] = [];

  constructor(private categoryService: CategoryService, 
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.listenToRouteParamChanges();
  }

  // called when any route param changes
  listenToRouteParamChanges() {
    this.route.params.subscribe(
      params => {
        this.searchTerm = params['searchTerm'];
        this.pageNumber = +params['pageNumber'];
        this.initializeCategoriesList();
    });
  }

  initializeCategoriesList(){
    this.categoryService.getCategories(this.searchTerm, this.pageNumber).subscribe(
      (response:HttpResponse<Category[]>) => { 
        this.totalPages = +response.headers.get('totalPages');
        this.categories = response.body; 
      },
      (error) => { console.log(error) }
    );
  }

  addNewCategory(): void {
    this.router.navigate(['categories', 'new']);
  }

  editCategory(categoryId: number): void {
    this.router.navigate(['categories', categoryId, 'edit']);
  }

  deleteCategory(categoryId: number): void {
    if (!confirm("Are you sure you want to delete selected record?")) {
      return;
    }
    this.categoryService.deleteCategory(categoryId).subscribe(
      (response: Response) => {
        if (response.status = "200") {
          this.router.navigate(['categories',  this.searchTerm + " ", this.pageNumber]);
        } else if (response.status = "404") {
          throw new Error(response.message);
        }
      },
      (error) => { console.log(error); }
    );
  }

  onSearchChange(event) {
    if(event.keyCode == 13){      // enter key pressed
      this.router.navigate(['categories', this.searchTerm, '1']);
    }
  }

  onPageChange(i: number): void {
    let pageNumber:number = this.pageNumber + i;
    this.router.navigate(['categories', this.searchTerm, pageNumber]);
  }



}
