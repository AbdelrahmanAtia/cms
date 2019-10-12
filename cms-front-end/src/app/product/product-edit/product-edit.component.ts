import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ProductService } from 'src/app/_services/product.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/_models/Product';
import { CategoryService } from 'src/app/_services/category.service';
import { Category } from 'src/app/_models/Category';

@Component({
  selector: 'app-product-edit',
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.css']
})
export class ProductEditComponent implements OnInit {

  
  editMode: boolean = false;
  productId: number;
  productForm: FormGroup;
  categories: Category[] = [];
  
  base64ProductImage: string | ArrayBuffer = '';

  constructor(private productService: ProductService,
              private categoryService: CategoryService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {

    this.productId = this.route.snapshot.params.id;
    this.editMode = this.productId != null;

    //initialize categories drop down list..
    this.categoryService.getCategories().subscribe(
      (response: Category[]) => { 
        this.categories = response; 
      },
      (error) => { console.log(error) }
    );

    //  initialize product form
    this.productForm = new FormGroup({
      'productName': new FormControl(null, Validators.required),
      'productDescription': new FormControl(null, null),
      'productCategory': new FormControl(null, Validators.required),
      'productPrice': new FormControl(null, Validators.required),
      'productImage': new FormControl(null, null),
      'productStatus': new FormControl(null, Validators.required)
    });

    if (this.editMode) {
      // populate product form in case of edit  mode
      this.productService.getProduct(this.productId).subscribe(
        (response: Product) => {
          this.productForm = new FormGroup({
            'productName': new FormControl(response.name, Validators.required),
            'productDescription': new FormControl(response.description, null),
            'productCategory': new FormControl(response.category.name, Validators.required),
            'productPrice': new FormControl(response.price, Validators.required),
            'productImage': new FormControl(null, null),
            'productStatus': new FormControl(response.active, Validators.required)
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

  getCategoryId(categoryName:string):number {
    for(let c of this.categories){
      console.log(c);
      if(c.name == categoryName){
        console.log("match found..");
        return c.id;
      }
    }
  }

  submitProductForm(): void {
    let product = new Product();
    product.id = this.productId;
    product.name = this.productForm.value.productName;
    product.description = this.productForm.value.productDescription;
    product.price = this.productForm.value.productPrice;
    product.image = this.base64ProductImage;
    product.active = this.productForm.value.productStatus;

    console.log(product.active);

    let category: Category = new Category();
    category.id = this.getCategoryId(this.productForm.value.productCategory);
    product.category = category;
   
    if (this.editMode)
      this.updateProduct(product);
    else
      this.addNewProduct(product);
  }

  addNewProduct(product: Product) {
    this.productService.addNewProduct(product).subscribe(
      (response: Product) =>  this.router.navigate(['products',' ', 0, 1])
      , (error) => console.log(error)
    );
  }

  updateProduct(product: Product) {
    this.productService.updateProduct(product).subscribe(
      (response: Product) => this.router.navigate(['products',' ', 0, 1])
      , (error) => console.log(error)
    );
  }

  cancel(): void {
    this.router.navigate(['products',' ', 0, 1]);
  }

}
