import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/_services/user.service';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { User } from 'src/app/_models/User';
import { AuthorityService } from 'src/app/_services/authority.service';
import { Authority } from 'src/app/_models/Authority';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {

  editMode: boolean = false;
  userId: number;
  userForm: FormGroup;

  userAuthorities: Authority[] = [];

  userStates: string[] = ['Active', 'InActive'];

  constructor(private userService: UserService,
              private authorityService: AuthorityService,
              private router: Router) { }

  ngOnInit() {

    //initialize authorities list
    this.initializeAuthoritiesList();

   //  initialize user form
   this.userForm = new FormGroup({
    'userEmail': new FormControl(null, Validators.required),
    'userPassword': new FormControl(null, Validators.required),
    'userName': new FormControl(null, Validators.required),
    'userPhone': new FormControl(null, null),
    'userState': new FormControl(null, Validators.required),
    'userAuthority': new FormControl(null, Validators.required)
   });

  }

  private initializeAuthoritiesList():void{
    this.authorityService.getAllAuthorities().subscribe((response: Authority[]) => {
      this.userAuthorities = response;
    }, (error) => { console.log(error); });
  }

  submitUserForm(): void {
    let user = new User();
    user.id = this.userId;

    let authorityName:string = this.userForm.value.userAuthority;
    let authority:Authority = this.getAuthorityByName(authorityName);

    user.authority.id = authority.id;
    user.email = this.userForm.value.userEmail;
    user.password = this.userForm.value.userPassword;
    user.name = this.userForm.value.userName;
    user.phone = this.userForm.value.userPhone;

    let state = this.userForm.value.userState;
    if(state == 'Active'){
      user.state = true;
    } else {
      user.state = false;
    }
    console.log(user)
    this.addNewUser(user);
  } 

  addNewUser(user: User) {
    this.userService.addNewUser(user).subscribe(
      (response: User) =>  this.router.navigate(['users'])
      , (error) => console.log(error)
    ); 
  }

  private getAuthorityByName(name:string):Authority {
    console.log("starting getAuthorityByName() with name = " + name);
    for(let userAuthority of this.userAuthorities){
      if(userAuthority.name == name){
        return userAuthority
      }
    }
    return null;
  }
  
  cancel(): void {
    this.router.navigate(['users']);
  }

} 
