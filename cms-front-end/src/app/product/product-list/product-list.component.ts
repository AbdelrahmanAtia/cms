import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/_models/Product';
import { ProductService } from 'src/app/_services/product.service';
import { Response } from 'src/app/_models/Response';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'src/app/_models/Category';
import { CategoryService } from 'src/app/_services/category.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  pageNumber: number = 1;
  totalPages: number;
  categoryId: number = 0;

  products: Product[] = [];
  categories: Category[] = [];

  //searchTerm: string;
  //searchMode: boolean = false;
  //searchForm: FormGroup;

  constructor(private productService: ProductService,
    private categoryService: CategoryService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.initializeCategoriesList();
    this.initializeProductsList();

    //this.initializeSearchForm();
    //this.listenToRouteParamChanges();
  }

  initializeCategoriesList() {
    //initialize categories drop down list..
    this.categoryService.getCategories().subscribe(
      (response: Category[]) => {
        this.categories = response;
      },
      (error) => { console.log(error) }
    );
  }

  initializeProductsList(): void {
    this.productService.getProducts(this.categoryId, this.pageNumber).subscribe(
      (response:HttpResponse<Product []>) => {
        this.totalPages = +response.headers.get('totalPages');
        this.products = response.body;
      },
      (error) => { console.log(error) }
    );
  }

  addNewProduct(): void {
    this.router.navigate(['products', 'new']);
  }

  editProduct(productId: number): void {
    this.router.navigate(['products', productId, 'edit']);
  }

  deleteProduct(productId: number): void {
    if (!confirm("Are you sure you want to delete selected record?")) {
      return;
    }
    this.productService.deleteProduct(productId).subscribe(
      (response: Response) => {
        if (response.status = "200") {
          console.log(response.message);
          this.router.navigate(['products']);
        }
        else if (response.status = "404") {
          throw new Error(response.message);
        }
      },
      (error) => { console.log(error); }
    );
  }

  onCategoryChange(event): void {
    this.pageNumber = 1;
    this.categoryId = event.target.value;
    this.initializeProductsList();
  }


  onPageChange(i: number): void {
    this.pageNumber = this.pageNumber + i;
    this.initializeProductsList();
  }

  /*
  // called when any route param changes
  listenToRouteParamChanges() {
    this.route.params.subscribe(
      params => {
        this.searchTerm = params['searchTerm'];
        this.searchMode = this.searchTerm != null;
        this.initializeProductsList();
      });
  }
  */

  /*
  initializeSearchForm(): void {
    this.searchForm = new FormGroup({
      'searchTerm': new FormControl("", Validators.required)
    });
  }
  */

  /*
  submitSearchForm(): void {
    let searchTerm: string = this.searchForm.value.searchTerm;
    this.router.navigate(['products', { 'searchTerm': searchTerm }]);
  }
  */

}
