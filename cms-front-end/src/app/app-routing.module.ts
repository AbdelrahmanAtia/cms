import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductComponent } from './_components/main/product/product.component';
import { ProductListComponent } from './_components/main/product/product-list/product-list.component';
import { CategoryComponent } from './_components/main/category/category.component';
import { CategoryListComponent } from './_components/main/category/category-list/category-list.component';
import { CategoryEditComponent } from './_components/main/category/category-edit/category-edit.component';
import { ProductEditComponent } from './_components/main/product/product-edit/product-edit.component';
import { UserComponent } from './_components/main/user/user.component';
import { UserListComponent } from './_components/main/user/user-list/user-list.component';
import { UserEditComponent } from './_components/main/user/user-edit/user-edit.component';
import { LoginComponent } from './_components/login/login.component';
import { DashBoardComponent } from './_components/main/dash-board/dash-board.component';
import { MainComponent } from './_components/main/main.component';
import { OrderComponent } from './_components/main/order/order.component';
import { OrderListComponent } from './_components/main/order/order-list/order-list.component';
import { OrderEditComponent } from './_components/main/order/order-edit/order-edit.component';
import { AuthGuard } from './_guards/auth.guard';
import { ProfileComponent } from './_components/main/profile/profile.component';

import { AdminGuard } from './_guards/admin.guard';

 
const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },

  {
    path: 'login', component: LoginComponent
  },
  
  {
    path: 'main', component: MainComponent, canActivate: [AuthGuard], children: 
      [

        {
          path: 'dashboard', component: DashBoardComponent
        },

        {
          path: 'products', component: ProductComponent, children:
            [
              { path: 'new', component: ProductEditComponent },
              { path: ':id/edit', component: ProductEditComponent },
              { path: ':searchTerm/:categoryId/:pageNumber', component: ProductListComponent },
              //this route to make current time stamp an optional parameter 
              { path: ':searchTerm/:categoryId/:pageNumber/:currentTimeStamp', component: ProductListComponent }
            ]
        },

        {
          path: 'categories', component: CategoryComponent, children:
            [
              { path: 'new', component: CategoryEditComponent },
              { path: ':id/edit', component: CategoryEditComponent },
              { path: ':searchTerm/:pageNumber', component: CategoryListComponent },
                //this route to make current time stamp an optional parameter 
              { path: ':searchTerm/:pageNumber/:currentTimeStamp', component: CategoryListComponent }

            ]
        },

        {
          path: 'orders', component: OrderComponent, children:
            [
              { path: 'new', component: OrderEditComponent },
              { path: ':id/edit', component: OrderEditComponent },
              { path: ':orderStatus/:pageNumber', component: OrderListComponent },
              //this route to make current time stamp an optional parameter 
              { path: ':orderStatus/:pageNumber/:currentTime', component: OrderListComponent },
            ]
        },
      
        {
          path: 'users', component: UserComponent, canActivate:[AdminGuard], children:
            [
              { path: 'new', component: UserEditComponent },
              { path: ':id/edit', component: UserEditComponent },
              { path: ':searchTerm/:userStatus/:pageNumber', component: UserListComponent },
              //this route to make current time stamp an optional parameter
              { path: ':searchTerm/:userStatus/:pageNumber/:currentTimeStamp', component: UserListComponent }
            ]
        },

        {
          path: 'profile', component: ProfileComponent
        }
      ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
