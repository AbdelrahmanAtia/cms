import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/_services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
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
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {

    this.userId = this.route.snapshot.params.id;
    this.editMode = this.userId != null;

    //initialize authorities list
    this.initializeAuthoritiesList();

    //  initialize user form
    this.initializeUsersForm(null, null, null, null, null, null);

    if (this.editMode) {
      //populate user form in case of edit mode
      this.userService.getUser(this.userId).subscribe(
        (response: User) => {
          console.log(response);
          this.initializeUsersForm(response.email,
            response.password,
            response.name,
            response.phone,
            ((response.active) ? this.userStates[0] : this.userStates[1]),
            response.authority.name);
        },
        (error) => console.log(error)
      );
    }
  }

  private initializeAuthoritiesList(): void {
    this.authorityService.getAllAuthorities().subscribe((response: Authority[]) => {
      this.userAuthorities = response;
    }, (error) => { console.log(error); });
  }

  private initializeUsersForm(userEmail: string,
    userPassword: string,
    userName: string,
    userPhone: string,
    userState: string,
    userAuthority: string): void {

    this.userForm = new FormGroup({
      'userEmail': new FormControl(userEmail, Validators.required),
      'userPassword': new FormControl(userPassword, Validators.required),
      'userName': new FormControl(userName, Validators.required),
      'userPhone': new FormControl(userPhone, null),
      'userState': new FormControl(userState, Validators.required),
      'userAuthority': new FormControl(userAuthority, Validators.required)
    });
  }

  submitUserForm(): void {
    let user = new User();
    user.id = this.userId;
    user.email = this.userForm.value.userEmail;
    user.password = this.userForm.value.userPassword;
    user.name = this.userForm.value.userName;
    user.phone = this.userForm.value.userPhone;
    user.active = (this.userForm.value.userState == 'Active')? true:false;

    let authorityName: string = this.userForm.value.userAuthority;
    let authority: Authority = this.getAuthorityByName(authorityName);
    user.authority.id = authority.id;
   
    if (this.editMode) {
      this.updateUser(user);
    } else {
      this.addNewUser(user);
    }

  }

  private addNewUser(user: User): void {
    this.userService.addNewUser(user).subscribe(
      (response: User) => this.router.navigate(['users'])
      , (error) => console.log(error)
    );
  }

  private updateUser(user: User): void {
    this.userService.updateUser(user).subscribe(
      (response: User) => this.router.navigate(['users'])
      , (error) => console.log(error)
    );
  }

  private getAuthorityByName(name: string): Authority {
    for (let userAuthority of this.userAuthorities) {
      if (userAuthority.name == name) {
        return userAuthority
      }
    }
    return null;
  }

  cancel(): void {
    this.router.navigate(['users']);
  }

} 
