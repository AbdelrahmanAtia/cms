import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductComponent } from './product/product.component';
import { ProductListComponent } from './product/product-list/product-list.component';


const routes: Routes = [
  { path: '', redirectTo: '/products', pathMatch: 'full' },

  { path: 'products', component: ProductComponent, children: 
      [
        { path: '', component: ProductListComponent }
      ]
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
