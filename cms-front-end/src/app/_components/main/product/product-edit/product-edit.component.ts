import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ProductService } from 'src/app/_services/product.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/_models/Product';
import { CategoryService } from 'src/app/_services/category.service';
import { Category } from 'src/app/_models/Category';
import { CustomValidator } from 'src/app/_validators/CustomValidator';

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
  base64ProductImage: string | ArrayBuffer = null;

  constructor(private productService: ProductService,
    private categoryService: CategoryService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {

    this.productId = this.route.snapshot.params.id;
    this.editMode = this.productId != null;

    this.initCategoriesList(); //initialize categories drop down list   

    if (this.editMode) {
      this.productService.getProduct(this.productId).subscribe(
        (response: Product) => {
          let categoryName: string = response.category ? response.category.name : null;

          this.initProductForm(response.name, response.description, categoryName, 
                               response.price, null, response.active);         
          
        }, (error) => { console.log(error); }
      );
    } else {
      this.initProductForm(null, null, null, null, null, null);
    }
  }

  private initCategoriesList():void{
    this.categoryService.getAllCategories().subscribe((response: Category[]) => {
      this.categories = response;
    }, (error) => { console.log(error); });
  }

  private initProductForm(productName:string, 
    productDescription:string, 
    productCategory:string,
    productPrice:number, 
    productImageName:string, 
    productStatus:boolean):void {

      this.productForm = new FormGroup({
        'productName': new FormControl(productName, Validators.required),
        'productDescription': new FormControl(productDescription, null),
        'productCategory': new FormControl(productCategory, Validators.required),
        'productPrice': new FormControl(productPrice, [Validators.required, CustomValidator.greaterThanZero]),
        'productImageName': new FormControl(productImageName, null),
        'productStatus': new FormControl(productStatus, Validators.required)
      });
  }

  readUrl(event: any) {
    if (event.target.files && event.target.files[0]) {
      var reader = new FileReader();
      reader.onload = (event: ProgressEvent) => {
        this.base64ProductImage = (<FileReader>event.target).result;
      }
      reader.readAsDataURL(event.target.files[0]);
    } else {
      // no image exist..so we need to reset image name and base 64 image
      this.productForm.patchValue({
        "productImageName" : null
      }); 
      this.base64ProductImage = null;
    }
  }

  getCategoryId(categoryName: string): number {
    for (let c of this.categories) {
      if (c.name == categoryName) {
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
    product.base64Image = this.base64ProductImage;
    product.active = this.productForm.value.productStatus;

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
      (response: Product) => this.router.navigate(['main', 'products', ' ', 0, 1])
      , (error) => console.log(error)
    );
  }

  updateProduct(product: Product) {
    this.productService.updateProduct(product).subscribe(
      (response: Product) => this.router.navigate(['main', 'products', ' ', 0, 1])
      , (error) => console.log(error)
    );
  }

  cancel(): void {
    this.router.navigate(['main', 'products', ' ', 0, 1]);
  }

}
