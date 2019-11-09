import { AbstractControl } from '@angular/forms';

export function GreaterThanZero(control: AbstractControl) {  
  if (control.value <= 0) {
    return { greaterThanZero: false };
  }
  return null;
}

