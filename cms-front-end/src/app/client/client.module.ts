import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClientRoutingModule } from './client-routing.module';
import { SharedModule } from 'src/app/shared/shared/shared.module';
import { ClientComponent } from './client.component';
import { HeaderComponent } from './_components/header/header.component';
import { CategoryListComponent } from './_components/category-list/category-list.component';


@NgModule({
  declarations: [
    ClientComponent,
    HeaderComponent,
    CategoryListComponent
  ],
  imports: [
    CommonModule,
    ClientRoutingModule,
    SharedModule
  ]
})
export class ClientModule { }
