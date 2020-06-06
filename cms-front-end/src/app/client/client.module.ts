import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClientRoutingModule } from './client-routing.module';
import { SharedModule } from 'src/app/shared/shared/shared.module';
import { ClientComponent } from './client.component';
import { HeaderComponent } from './_components/header/header.component';
import { CategoryListComponent } from './_components/category-list/category-list.component';
import { ProductListComponent } from './_components/product-list/product-list.component';
import { CartComponent } from './_components/cart/cart.component';


@NgModule({
  declarations: [
    ClientComponent,
    HeaderComponent,
    CategoryListComponent,
    ProductListComponent,
    CartComponent
  ],
  imports: [
    CommonModule,
    ClientRoutingModule,
    SharedModule
  ]
})
export class ClientModule { }
