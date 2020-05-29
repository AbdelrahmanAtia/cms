import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ClientComponent } from './client.component';
import { CategoryListComponent } from './_components/category-list/category-list.component';
import { ProductListComponent } from './_components/product-list/product-list.component';

const routes: Routes = [
  {
    path: 'client', component: ClientComponent, children:
      [
        { path: 'categories', component: CategoryListComponent },
        { path: 'categories/:categoryId/products', component: ProductListComponent }
      ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientRoutingModule { }
