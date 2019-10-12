import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductComponent } from './product/product.component';
import { ProductListComponent } from './product/product-list/product-list.component';
import { CategoryComponent } from './category/category.component';
import { CategoryListComponent } from './category/category-list/category-list.component';
import { CategoryEditComponent } from './category/category-edit/category-edit.component';
import { ProductEditComponent } from './product/product-edit/product-edit.component';

 
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
        { path: '', component: CategoryListComponent },
        { path: 'new', component: CategoryEditComponent },
        { path: ':id/edit', component: CategoryEditComponent }
      ]
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
