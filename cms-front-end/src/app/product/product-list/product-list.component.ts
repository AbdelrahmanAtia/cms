import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/_models/Product';
import { ProductService } from 'src/app/_services/product.service';
import { Response } from 'src/app/_models/Response';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'src/app/_models/Category';
import { CategoryService } from 'src/app/_services/category.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  searchTerm: string;
  searchMode: boolean = false;

  products: Product[] = [];
  searchForm: FormGroup;

  categories: Category[] = [];

  constructor(private productService: ProductService,
    private categoryService: CategoryService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.initializeSearchForm();
    this.initializeCategoriesList();
    this.listenToRouteParamChanges();
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

  // called when any route param changes
  listenToRouteParamChanges() {
    this.route.params.subscribe(
      params => {
        this.searchTerm = params['searchTerm'];
        this.searchMode = this.searchTerm != null;
        this.initializeProductsList();
      });
  }

  initializeSearchForm(): void {
    this.searchForm = new FormGroup({
      'searchTerm': new FormControl("", Validators.required)
    });
  }

  initializeProductsList(): void {
    console.log('initializeProductsList() called..')
    this.productService.getProducts(this.searchMode, this.searchTerm).subscribe(
      (response: Product[]) => {
        this.products = response;
      },
      (error) => { console.log(error) }
    );
  }

  submitSearchForm(): void {
    let searchTerm: string = this.searchForm.value.searchTerm;
    this.router.navigate(['products', { 'searchTerm': searchTerm }]);
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

  onChange(event): void {
    console.log(event.target.value);
    let categoryId: number = event.target.value;
    this.productService.getProductsByCategoryId(categoryId).subscribe(
      (response: Product[]) => {
        this.products = response;
      }, (error) => {
        console.log(error);
      }
    );
  }

}
