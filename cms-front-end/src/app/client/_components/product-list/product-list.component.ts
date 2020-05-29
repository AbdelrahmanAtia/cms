import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from 'src/app/admin/_services/product.service';
import { HttpResponse } from '@angular/common/http';
import { Product } from 'src/app/admin/_models/Product';
import { Config } from 'src/app/admin/_models/Config ';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  categoryId:number;
  searchTerm:string = '';
  pageNumber:number = 1;
  products: Product[] = []; 
  imageBaseUrl:string = new Config().baseUrl + "/products/getImage";


  constructor(private activatedRoute: ActivatedRoute,
              private productService: ProductService) { }

  ngOnInit() {
    this.listenToRouteParamChanges();
  }

   // called when any route param changes
   listenToRouteParamChanges() {
    this.activatedRoute.params.subscribe(
      params => {
        this.categoryId = +params['categoryId'];
        this.initializeProductsList();
      });
  }

  initializeProductsList(): void {
    this.productService.getProducts(this.searchTerm, this.categoryId, this.pageNumber).subscribe(
      (response:HttpResponse<Product []>) => {
        //this.totalPages = +response.headers.get('totalPages');
        this.products = response.body;
        console.log(this.products);
      },
      (error) => { console.log(error) }
    );
  }
   
}
