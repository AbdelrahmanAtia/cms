import { Component, OnInit } from '@angular/core';
import { CategoryService } from 'src/app/_services/category.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Category } from 'src/app/_models/Category';

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

    //  initialize category form
    this.categoryForm = new FormGroup({
      'categoryName': new FormControl(null, Validators.required),
      'categoryDescription': new FormControl(null, null),
      'categoryImage': new FormControl(null, null)
    });

    this.categoryId = this.route.snapshot.params.id;
    this.editMode = this.categoryId != null;

    if (this.editMode) {
      // populate category form in case of edit  mode
      this.categoryService.getCategory(this.categoryId).subscribe(
        (response: Category) => {
          this.categoryForm = new FormGroup({
            'categoryName': new FormControl(response.name, Validators.required),
            'categoryDescription': new FormControl(response.description, null),
            'categoryImage': new FormControl(null, null)
          });
          this.base64CategoryImage = response.image;
        }, (error) => { console.log(error); }
      );
    }
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
    if (this.editMode)
      this.updateCategory(category);
    else
      this.addNewCategory(category);
  }

  addNewCategory(category: Category) {
    this.categoryService.addNewCategory(category).subscribe(
      (response: Category) => this.router.navigate(['/categories'])
      , (error) => console.log(error)
    ); 
  }

  updateCategory(category: Category) {
    this.categoryService.updateCategory(category).subscribe(
      (response: Category) => this.router.navigate(['/categories'])
      , (error) => console.log(error)
    );
  }

  cancel(): void {
    this.router.navigate(['/categories']);
  }

}
