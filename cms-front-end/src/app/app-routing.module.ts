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
              { path: ':searchTerm/:categoryId/:pageNumber', component: ProductListComponent },
              { path: 'new', component: ProductEditComponent },
              { path: ':id/edit', component: ProductEditComponent }
            ]
        },

        {
          path: 'categories', component: CategoryComponent, children:
            [
              { path: 'new', component: CategoryEditComponent },
              { path: ':id/edit', component: CategoryEditComponent },
              { path: ':searchTerm/:pageNumber', component: CategoryListComponent }
            ]
        },

        {
          path: 'orders', component: OrderComponent, children:
            [
              { path: '', component: OrderListComponent },
              { path: 'new', component: OrderEditComponent },
              { path: ':id/edit', component: OrderEditComponent }
            ]
        },
      
        {
          path: 'users', component: UserComponent, children:
            [
              { path: 'new', component: UserEditComponent },
              { path: ':id/edit', component: UserEditComponent },
              { path: ':searchTerm/:userStatus/:pageNumber', component: UserListComponent }
            ]
        }
      ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
