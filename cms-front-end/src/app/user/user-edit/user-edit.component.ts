import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/_services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {

  editMode: boolean = false;
  userId: number;
  userForm: FormGroup;

  constructor(private userService: UserService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    
   //  initialize user form
   this.userForm = new FormGroup({
    'userEmail': new FormControl(null, Validators.required),
    'userPassword': new FormControl(null, Validators.required),
    'userName': new FormControl(null, Validators.required),
    'userPhone': new FormControl(null, null)
   });


  }

  submitUserForm(): void {
    
  }

}
