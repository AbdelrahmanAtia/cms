import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ClientComponent } from './client.component';
import { CategoryListComponent } from './_components/category-list/category-list.component';
import { ProductListComponent } from './_components/product-list/product-list.component';
import { CartComponent } from './_components/cart/cart.component'
import { OrderPreviewComponent } from './_components/order-preview/order-preview.component';
const routes: Routes = [
  {
    path: 'client', component: ClientComponent, children:
      [
        { path: 'categories', component: CategoryListComponent },
        { path: 'categories/:categoryId/products', component: ProductListComponent },
        { path: 'cart', component: CartComponent},
        { path: 'order/preview', component: OrderPreviewComponent}
      ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientRoutingModule { }
