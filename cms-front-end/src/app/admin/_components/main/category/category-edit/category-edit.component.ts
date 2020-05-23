import { Component, OnInit } from '@angular/core';
import { CategoryService } from 'src/app/admin/_services/category.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Category } from 'src/app/admin/_models/Category';
import { Response } from 'src/app/admin/_models/Response';
import { CustomValidator } from 'src/app/admin/_validators/CustomValidator';
import { Config } from 'src/app/admin/_models/Config ';


@Component({
  selector: 'app-category-edit',
  templateUrl: './category-edit.component.html',
  styleUrls: ['./category-edit.component.css']
})
export class CategoryEditComponent implements OnInit {

  editMode: boolean = false;
  categoryId: number;
  categoryForm: FormGroup;
  base64CategoryImage: string | ArrayBuffer = null;
  imageBaseUrl:string = new Config().baseUrl + "/categories/getImage";
  imageName:string = null;  //only set in case edit mode


  constructor(private categoryService: CategoryService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {

    this.categoryId = this.route.snapshot.params.id;
    this.editMode = this.categoryId != null;

    if (this.editMode) {
      
      this.categoryService.getCategory(this.categoryId).subscribe(
        (response: Category) => {
          this.imageName = response.imageName; //to preview image
          this.initializeCategoryForm(response.name, response.description, null, response.productCount);
        }, (error) => { 
          console.log(error); 
        }
      );
    } else {
      this.initializeCategoryForm(null, null, null, null);
    }
  }  
 
  private initializeCategoryForm(categoryName:string, 
                                 categoryDescription:string,
                                 categoryImage:string,
                                 productCount:number):void {
      this.categoryForm = new FormGroup({
        'categoryName': new FormControl(categoryName, [Validators.required, CustomValidator.notBlank], CustomValidator.uniqueCategoryName(this.categoryService, this.categoryId)),
        'categoryDescription': new FormControl(categoryDescription, null),
        'categoryImageName': new FormControl(categoryImage, null),
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
    } else {
      // no image exist..so we need to reset image name and base 64 image
      this.categoryForm.patchValue({
        "categoryImageName" : null
      }); 
      this.base64CategoryImage = null;
    }
  }

  submitCategoryForm(): void {
    let category = new Category();
    category.id = this.categoryId;
    category.name = this.categoryForm.value.categoryName;
    category.description = this.categoryForm.value.categoryDescription;
    category.base64Image = this.base64CategoryImage;
    category.productCount = this.categoryForm.value.productCount;
    
    if (this.editMode)
      this.updateCategory(category);
    else
      this.addNewCategory(category);
  }

  private addNewCategory(category: Category):void {
    this.categoryService.addNewCategory(category).subscribe(
      (response: Category) =>  this.router.navigate(Config.categoryListRoute)
      , (error) => console.log(error)
    ); 
  }

  private updateCategory(category: Category):void {
    this.categoryService.updateCategory(category).subscribe(
      (response: Category) => this.router.navigate(Config.categoryListRoute)
      , (error) => console.log(error)
    );
  }

  cancel(): void {
    this.router.navigate(Config.categoryListRoute);
  }

  deleteCategoryImage():void {
    if (!confirm("Are you sure that you want to delete this image?")) {
      return;
    }
    
    this.categoryService.deleteCategoryImage(this.imageName).subscribe(
      (response:Response) => {
        if(response.status){
          this.imageName = "no_image.png"
        }else {
          throw new Error(response.message);
        }
      },
      (error) => {
       console.log(error);
      }  
    )
  }

}
