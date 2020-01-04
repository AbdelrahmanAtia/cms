import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/_services/user.service';
import { User } from 'src/app/_models/User';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: User[] = [];

  constructor(private userService: UserService,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.listenToRouteParamChanges();
  }

  // called when any route param changes
  listenToRouteParamChanges() {
    this.route.params.subscribe(
      params => {
        this.initializeUsersList();
      });
  }

  initializeUsersList(): void {
    this.userService.getAllUsers().subscribe(
      (response: User[]) => {
        this.users = response;
        for(let user of this.users){
          if(user.authority.name == 'Administrator'){
            user.authority.name = 'admin';
          } else if(user.authority.name == 'Editor'){
            user.authority.name = 'editor';
          }
        }
      },
      (error) => { console.log(error) }
    );
  }

  addNewUser(): void {
    this.router.navigate(['users', 'new']);
  }

  editUser(userId:number):void {
    this.router.navigate(['users', userId, 'edit']);
  }

}
