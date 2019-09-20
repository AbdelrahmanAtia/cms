import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/_models/Product';
import { ProductService } from 'src/app/_services/product.service';
import { Router } from '@angular/router';
import { Response } from 'src/app/_models/Response';
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
