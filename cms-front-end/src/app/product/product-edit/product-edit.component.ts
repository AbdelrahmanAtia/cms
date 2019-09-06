import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ProductService } from 'src/app/_services/product.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/_models/Product';

@Component({
  selector: 'app-product-edit',
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.css']
})
export class ProductEditComponent implements OnInit {

  
  editMode: boolean = false;
  productId: number;
  productForm: FormGroup;
  base64ProductImage: string | ArrayBuffer = '';


  constructor(private productService: ProductService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {

    //  initialize category form
    this.productForm = new FormGroup({
      'productName': new FormControl(null, Validators.required),
      'productDescription': new FormControl(null, null),
      'productImage': new FormControl(null, null)
    });

    this.productId = this.route.snapshot.params.id;
    this.editMode = this.productId != null;

    if (this.editMode) {
      // populate product form in case of edit  mode
      this.productService.getProduct(this.productId).subscribe(
        (response: Product) => {
          this.productForm = new FormGroup({
            'categoryName': new FormControl(response.name, Validators.required),
            'categoryDescription': new FormControl(response.description, null),
            'categoryImage': new FormControl(null, null)
          });
          this.base64ProductImage = response.image;
        }, (error) => { console.log(error); }
      );
    }
  }

  readUrl(event: any) {
    if (event.target.files && event.target.files[0]) {
      var reader = new FileReader();
      reader.onload = (event: ProgressEvent) => {
        this.base64ProductImage = (<FileReader>event.target).result;
      }
      reader.readAsDataURL(event.target.files[0]);
    }
  }

  submitProductForm(): void {
    let product = new Product();
    product.id = this.productId;
    product.name = this.productForm.value.productName;
    product.description = this.productForm.value.productDescription;
    product.image = this.base64ProductImage;
    if (this.editMode)
      this.updateProduct(product);
    else
      this.addNewProduct(product);
  }

  addNewProduct(product: Product) {
    this.productService.addNewProduct(product).subscribe(
      (response: Product) => this.router.navigate(['/products'])
      , (error) => console.log(error)
    );
  }

  updateProduct(product: Product) {
    this.productService.updateProduct(product).subscribe(
      (response: Product) => this.router.navigate(['/products'])
      , (error) => console.log(error)
    );
  }

  cancel(): void {
    this.router.navigate(['/products']);
  }

}
