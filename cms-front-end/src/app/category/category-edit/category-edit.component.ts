import { Component, OnInit } from '@angular/core';
import { CategoryService } from 'src/app/_services/category.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormControl, Validators, NgForm } from '@angular/forms';
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

  constructor(private categoryService: CategoryService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {

    //  initialize category form
    this.categoryForm = new FormGroup({
      'categoryName': new FormControl(null, Validators.required),
      'categoryDescription': new FormControl(null, null)
    });

    this.categoryId = this.route.snapshot.params.id;
    this.editMode = this.categoryId != null;

    if (this.editMode) {
      // populate category form in case of edit  mode
      this.categoryService.getCategory(this.categoryId).subscribe(
        (response: Category) => {
          this.categoryForm = new FormGroup({
            'categoryName': new FormControl(response.name, Validators.required),
            'categoryDescription': new FormControl(response.description, null)
          });
        }, (error) => { console.log(error); }
      );
    }
  }

  submitCategoryForm(): void {
    let category = new Category();
    category.id = this.categoryId;
    category.name = this.categoryForm.value.categoryName;
    category.description = this.categoryForm.value.categoryDescription;
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
