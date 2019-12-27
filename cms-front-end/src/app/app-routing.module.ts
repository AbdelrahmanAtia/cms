import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductComponent } from './product/product.component';
import { ProductListComponent } from './product/product-list/product-list.component';
import { CategoryComponent } from './category/category.component';
import { CategoryListComponent } from './category/category-list/category-list.component';
import { CategoryEditComponent } from './category/category-edit/category-edit.component';
import { ProductEditComponent } from './product/product-edit/product-edit.component';
import { OrderComponent } from './order/order.component';
import { OrderListComponent } from './order/order-list/order-list.component';
import { OrderEditComponent } from './order/order-edit/order-edit.component';
import { UserComponent } from './user/user.component';
import { UserListComponent } from './user/user-list/user-list.component';
import { UserEditComponent } from './user/user-edit/user-edit.component';

 
const routes: Routes = [
  { path: '', redirectTo: '/categories', pathMatch: 'full' },

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
        { path: '', component: UserListComponent },
        { path: 'new', component: UserEditComponent },
        { path: ':id/edit', component: UserEditComponent }
      ]
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
