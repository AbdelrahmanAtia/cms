import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/_services/user.service';
import { User } from 'src/app/_models/User';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Response } from 'src/app/_models/Response';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  searchTerm: string;
  userStatus: string;
  pageNumber: number;
  totalPages: number;
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
        this.searchTerm = (params['searchTerm'] + "").trim();
        this.userStatus = params['userStatus'];
        this.pageNumber = +params['pageNumber'];
        this.initializeUsersList();
      });
  }

  initializeUsersList(): void {
    this.userService.getUsers(this.searchTerm, this.userStatus, this.pageNumber).subscribe(
      (response: HttpResponse<User[]>) => {
        this.totalPages = +response.headers.get('totalPages');
        this.users = response.body;
        for (let user of this.users) {
          if (user.authority.name == 'Administrator') {
            user.authority.name = 'admin';
          } else if (user.authority.name == 'Editor') {
            user.authority.name = 'editor';
          }
        }
      },
      (error) => { console.log(error) }
    );
  }

  addNewUser(): void {
    this.router.navigate(['main', 'users', 'new']);
  }

  editUser(userId: number): void {
    this.router.navigate(['main', 'users', userId, 'edit']);
  }

  deleteUser(userId: number): void {

    if (!confirm("Are you sure you want to delete selected record?")) {
      return;
    }
    this.userService.deleteUser(userId).subscribe(
      (response: Response) => {
        if (response.status) {
          if (this.pageNumber > 1 && this.totalPages == this.pageNumber && this.users.length == 1) {
            //go to the previous page 
            this.router.navigate(['main', 'users', (this.searchTerm.length > 0) ? this.searchTerm : " ", this.userStatus, this.pageNumber - 1]);
          } else {
            //stay in the same page
            this.router.navigate(['main', 'users', (this.searchTerm.length > 0) ? this.searchTerm : " ", this.userStatus, this.pageNumber, new Date().getTime()]);
          }
        } else {
          throw new Error(response.message);
        }
      },
      (error) => { console.log(error); }
    );
  }

  onSearchChange(event) {
    if (event.keyCode == 13) {      // enter key pressed
      this.router.navigate(['main', 'users', (this.searchTerm.length > 0) ? this.searchTerm : " ", 'All', '1']);
    }
  }

  onPageChange(i: number): void {
    let pageNumber: number = this.pageNumber + i;
    this.router.navigate(['main', 'users', (this.searchTerm.length > 0) ? this.searchTerm : " ", this.userStatus, pageNumber]);
  }

  onStatusChange(status: string): void {
    this.router.navigate(['main', 'users', ' ', status, '1']);
  }

}
