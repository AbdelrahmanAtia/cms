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

    this.categoryId = this.route.snapshot.parent.params.id;
    this.editMode = this.categoryId != null;

    if (this.editMode) {
      throw new Error("Not implemented");
    } else {
      this.categoryForm = new FormGroup({
        'categoryName': new FormControl(null, Validators.required),
        'categoryDescription': new FormControl(null, null)
      });
    }
  }

  submitCategoryForm(): void {    
    let category = new Category();
    category.name = this.categoryForm.value.categoryName;
    category.description = this.categoryForm.value.categoryDescription;
    
    this.categoryService.addNewCategory(category).subscribe(

      (response:Category) =>{
        console.log("category saved to db: " + response);
        this.router.navigate(['/categories']);
      },(error) => {
        console.log(error);
      }


    );

    //console.log(category);
 
  }

  cancel():void {
    this.router.navigate(['/categories']);
  }

}
