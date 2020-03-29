import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/_models/Product';
import { ProductService } from 'src/app/_services/product.service';
import { Response } from 'src/app/_models/Response';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'src/app/_models/Category';
import { CategoryService } from 'src/app/_services/category.service';
import { HttpResponse } from '@angular/common/http';
import { Config } from 'src/app/_models/Config ';

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

  imageBaseUrl:string = new Config().baseUrl + "/products/getImage";

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
        this.searchTerm = (params['searchTerm'] + "").trim();
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
    this.router.navigate(['main', 'products', 'new']);
  }

  editProduct(productId: number): void {
    this.router.navigate(['main', 'products', productId, 'edit']);
  }

  deleteProduct(productId: number): void {
    if (!confirm("Are you sure you want to delete selected record?")) {
      return;
    }
    this.productService.deleteProduct(productId).subscribe(
      (response: Response) => {
        if (response.status = "200") { 
          if (this.pageNumber > 1 && this.totalPages == this.pageNumber && this.products.length == 1) {
            //go to the previous page 
            this.router.navigate(['main', 'products', this.searchTerm, this.categoryId, this.pageNumber - 1]);
          } else {
            //stay in the same page and remove the deleted record from the list
            let updatedProductsList: Product[] = this.products.filter((element: Product, index: number, list: Product[]) => {
              return (element.id != productId);
            });
            this.products = updatedProductsList;
          }
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
      let categoryId : number = 0;  // reset category 
      let pageNumber: number = 1;   // reset page
      this.router.navigate(['main', 'products', this.searchTerm, categoryId, pageNumber]);
    }
  }

  onCategoryChange(event): void {
    let categoryId: number = event.target.value;
    let searchTerm:string = ' ';  //reset search term
    let pageNumber: number = 1;   // reset page 
    this.router.navigate(['main', 'products', searchTerm, categoryId, pageNumber]);
  }

  onPageChange(i: number): void {
    let pageNumber:number = this.pageNumber + i;

    // paginate on the current category & search term
    let categoryId: number = this.categoryId;  
    let searchTerm: string = (this.searchTerm.length == 0)? ' ': this.searchTerm;

    this.router.navigate(['main', 'products', searchTerm, categoryId, pageNumber]);
  }

  goToPage(i:number){
    if(i < 1 || i > this.totalPages){
      return;
    }
    this.pageNumber = i;
    this.initializeProductsList();
  }

}
