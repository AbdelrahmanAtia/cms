import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ProfileService } from 'src/app/_services/profile.service';
import { Profile } from 'src/app/_models/Profile';
import { CustomValidator } from 'src/app/_validators/CustomValidator';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/_services/authentication.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  profileForm: FormGroup;
  hiddenAlert:boolean = true;

  constructor(private profileService: ProfileService,
              private router: Router,
              private authenticationService: AuthenticationService) { }

  ngOnInit() {

    this.initializeProfileForm(null, null, null, null);

    //retreive user profile
    this.profileService.getCurrentUserProfile().subscribe(
      (response: Profile) => {
        this.initializeProfileForm(response.email, response.password, response.name, response.phone);
      },
      (error) => {
        console.log(error);
      }
    );

  }

  initializeProfileForm(userEmail: string, userPassword: string, userName: string, userPhone: string) {
    this.profileForm = new FormGroup(
      {
        'userEmail': new FormControl(userEmail, [Validators.required, Validators.email], [CustomValidator.uniqueProfileEmail(this.profileService)]),
        'userPassword': new FormControl(userPassword, [Validators.required, CustomValidator.notBlank]),
        'userName': new FormControl(userName, [Validators.required, CustomValidator.notBlank]),
        'userPhone': new FormControl(userPhone)
      }
    );
  }

  submitProfileForm(): void {
    let profile = new Profile();

    profile.email = this.profileForm.value.userEmail;
    profile.password = this.profileForm.value.userPassword;
    profile.name = this.profileForm.value.userName;
    profile.phone = this.profileForm.value.userPhone;
 
    this.profileService.updateCurrentUserProfile(profile).subscribe(
      (response: Profile) => {
        console.log('profile info updated..');
        this.hiddenAlert = false;
        //update authentication token
        this.authenticationService.updateAuthenticationToken(response.name, response.password);
        //view a message that profile is updated
      },
      (error) => {
        console.log(error);
      }
    );

  }

  hideAlert():void {
    this.hiddenAlert = true;
  }


}
