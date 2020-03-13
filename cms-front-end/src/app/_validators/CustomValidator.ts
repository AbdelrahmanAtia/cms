import { AbstractControl, ValidationErrors } from '@angular/forms';
import { UserService } from '../_services/user.service';
import { CategoryService } from '../_services/category.service';

export function GreaterThanZero(control: AbstractControl) {
  if (control.value <= 0) {
    return { greaterThanZero: false };
  }
  return null;
}

export class CustomValidator {


  static uniqueCategoryName(categoryService: CategoryService, categoryId: number) {
    return (control: AbstractControl) => {
      let categoryName: string = control.value;
      if (categoryId == undefined) {
        categoryId = 0;
      }
      return categoryService.isUniqueCategoryName(categoryName, categoryId);
    }
  }

  static uniqueEmail(userService: UserService, userId: number) {
    return (control: AbstractControl) => {
      let email: string = control.value;
      if (userId == undefined) {
        userId = 0;
      }
      return userService.isUniqueEmail(email, userId);
    }
  }

  static notBlank(control: AbstractControl): ValidationErrors {
    if (control.value && control.value.trim().length > 0) {
      return null;  //validation passes
    }
    //validation fails
    return { blank: true };
  }

  static DateAfterNow(editMode: boolean) {
    return (control: AbstractControl) : ValidationErrors => {
      let controlValue: number = new Date(control.value).getTime();
      let currentDateTime: number = new Date().getTime();
      if (editMode || controlValue > currentDateTime) {
        return null;   //validation passes
      }
      return { inValidDate: true }  //validation fails
    }
  }



}
