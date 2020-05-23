import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { User } from '../../_models/User';
import { AuthenticationService } from '../../_services/authentication.service';
import { Router } from '@angular/router';
import { Config } from '../../_models/Config ';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  invalidLogin:boolean;
  
  constructor(private authenticationService:AuthenticationService,
              private router: Router) { }

  ngOnInit() {
    this.loginForm = new FormGroup({
      'userEmail': new FormControl(null, [Validators.required, Validators.email]),
      'userPassword': new FormControl(null, Validators.required)
    });
  }
 
  submitLoginForm(): void {
    let user = new User();
    user.email = this.loginForm.value.userEmail;
    user.password = this.loginForm.value.userPassword;

    this.authenticationService.login(user).subscribe(
      (response:User) => {
        this.authenticationService.setAuthToken(response);
        //redirect to dashboard..
        this.router.navigate(Config.dashBoardRoute);
      }
      ,
      (error) => {
        console.log(error);
        if(error.status == 401){
          this.invalidLogin = true;
        }
      }
    );
  }


}
