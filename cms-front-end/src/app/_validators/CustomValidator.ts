import { AbstractControl, ValidationErrors } from '@angular/forms';
import { UserService } from '../_services/user.service';

export function GreaterThanZero(control: AbstractControl) {
  if (control.value <= 0) {
    return { greaterThanZero: false };
  }
  return null;
}

export class CustomValidator {

  static uniqueEmail(userService: UserService) {
    return (control: AbstractControl) => {
      let email: string = control.value;
      return userService.isUniqueEmail(email);
    }
  }

  static notBlank(control: AbstractControl): ValidationErrors {
    if(control.value && control.value.trim().length > 0){
      return null;  //validation passes
    } 
    //validation fails
    return { blank : true};
  }

}
