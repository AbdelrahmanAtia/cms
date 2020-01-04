import { Component, OnInit } from '@angular/core';
import { CategoryService } from 'src/app/_services/category.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Category } from 'src/app/_models/Category';
import { Response } from 'src/app/_models/Response';

@Component({
  selector: 'app-category-edit',
  templateUrl: './category-edit.component.html',
  styleUrls: ['./category-edit.component.css']
})
export class CategoryEditComponent implements OnInit {

  editMode: boolean = false;
  categoryId: number;
  categoryForm: FormGroup;
  base64CategoryImage: string | ArrayBuffer = '';

  constructor(private categoryService: CategoryService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {

    this.categoryId = this.route.snapshot.params.id;
    this.editMode = this.categoryId != null;

    //  initialize category form
    this.initializeCategoryForm(null, null, null, null);

    if (this.editMode) {
      // populate category form in case of edit  mode
      this.categoryService.getCategory(this.categoryId).subscribe(
        (response: Category) => {
          this.initializeCategoryForm(response.name, response.description, null, response.productCount);
          this.base64CategoryImage = response.image;
        }, (error) => { console.log(error); }
      );
    }
  }

  private initializeCategoryForm(categoryName:string, 
                                 categoryDescription:string,
                                 categoryImage:string,
                                 productCount:number):void {
      this.categoryForm = new FormGroup({
        'categoryName': new FormControl(categoryName, Validators.required),
        'categoryDescription': new FormControl(categoryDescription, null),
        'categoryImage': new FormControl(categoryImage, null),
        'productCount': new FormControl(productCount, null)
      });
  }

  readUrl(event: any) {
    if (event.target.files && event.target.files[0]) {
      var reader = new FileReader();
      reader.onload = (event: ProgressEvent) => {
        this.base64CategoryImage = (<FileReader>event.target).result;
      }
      reader.readAsDataURL(event.target.files[0]);
    }
  }

  /*
  convertBase64StringToBytes(imageBase64String): number[] {
    if (imageBase64String.length == 0) {
      return [];
    }
    let BASE64_MARKER: string = ';base64,';
    let base64Index: number = imageBase64String.indexOf(BASE64_MARKER) + BASE64_MARKER.length;
    let base64: string = imageBase64String.substring(base64Index);
    let decodedString: string = window.atob(base64);
    let bytesArray: number[] = [];
    for (let i = 0; i < decodedString.length; i++) {
      bytesArray.push(decodedString.charCodeAt(i));
    }
    return bytesArray;
  }
  */

  submitCategoryForm(): void {
    let category = new Category();
    category.id = this.categoryId;
    category.name = this.categoryForm.value.categoryName;
    category.description = this.categoryForm.value.categoryDescription;
    category.image = this.base64CategoryImage;
    category.productCount = this.categoryForm.value.productCount;
    
    if (this.editMode)
      this.updateCategory(category);
    else
      this.addNewCategory(category);
  }

  private addNewCategory(category: Category):void {
    this.categoryService.addNewCategory(category).subscribe(
      (response: Category) =>  this.router.navigate(['categories', ' ', '1'])
      , (error) => console.log(error)
    ); 
  }

  private updateCategory(category: Category):void {
    this.categoryService.updateCategory(category).subscribe(
      (response: Category) => this.router.navigate(['categories', ' ', '1'])
      , (error) => console.log(error)
    );
  }

  cancel(): void {
    this.router.navigate(['categories', ' ', '1']);
  }

  deleteCategoryImage():void {

    if(!this.editMode ){
      this.base64CategoryImage = null;
      this.categoryForm.patchValue({categoryImage: null});
      return;
    } 

    if (!confirm("Are you sure that you want to delete this image?")) {
      return;
    }

    this.categoryService.deleteCategoryImage(this.categoryId).subscribe(
      (response: Response) => {
        if(response.status == '200'){
          this.base64CategoryImage = null;
          this.categoryForm.patchValue({categoryImage: null});
        } else {
          console.log(response);
        }
      }, 
      (error) => console.log(error)
    );
  }

}
