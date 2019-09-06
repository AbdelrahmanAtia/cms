import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/_models/Product';
import { ProductService } from 'src/app/_services/product.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[] = [];

  constructor(private productService: ProductService,
              private router: Router) {}

  ngOnInit() {
    this.productService.getProducts().subscribe(
      (response: Product[]) => { 
        this.products = response; 
        console.log(this.products);
      },
      (error) => {console.log(error)}
    );
  }
  
  addNewProduct(): void { 
    this.router.navigate(['products', 'new']);
  }

}
