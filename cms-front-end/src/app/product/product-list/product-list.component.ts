import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/_models/Product';
import { ProductService } from 'src/app/_services/product.service';
import { Response } from 'src/app/_models/Response';
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

  searchTerm: string;
  categoryId: number;
  pageNumber: number;
  totalPages: number;

  products: Product[] = []; 
  categories: Category[] = [];

  constructor(private productService: ProductService,
    private categoryService: CategoryService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.initializeCategoriesList();
    this.listenToRouteParamChanges();
  }

  // called when any route param changes
  listenToRouteParamChanges() {
    this.route.params.subscribe(
      params => {
        this.searchTerm = params['searchTerm'];
        this.categoryId = +params['categoryId'];
        this.pageNumber = +params['pageNumber'];
        this.initializeProductsList();
      });
  }

  initializeCategoriesList() {
    //initialize categories drop down list..
    this.categoryService.getAllCategories().subscribe(
      (response: Category[]) => {
        this.categories = response;
      },
      (error) => { console.log(error) }
    );
  }

  initializeProductsList(): void {
    this.productService.getProducts(this.searchTerm, this.categoryId, this.pageNumber).subscribe(
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
          this.router.navigate(['products', this.searchTerm + " ", this.categoryId, this.pageNumber]);
        }
        else if (response.status = "404") {
          throw new Error(response.message);
        }
      },
      (error) => { console.log(error); }
    );
  }

  onSearchChange(event) {
    if(event.keyCode == 13){      // enter key pressed
      this.router.navigate(['products', this.searchTerm, this.categoryId, '1']);
    }
  }

  onCategoryChange(event): void {
    let categoryId: number = event.target.value;
    this.router.navigate(['products', this.searchTerm, categoryId, '1']);
  }

  onPageChange(i: number): void {
    let pageNumber:number = this.pageNumber + i;
    this.router.navigate(['products', this.searchTerm, this.categoryId, pageNumber]);
  }

  goToPage(i:number){
    if(i < 1 || i > this.totalPages){
      return;
    }
    this.pageNumber = i;
    this.initializeProductsList();
  }

}
