import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ClientComponent } from './client.component';
import { CategoryListComponent } from './_components/category-list/category-list.component';


const routes: Routes = [

  {
    path: 'client', component: ClientComponent, children: [
      { path: 'categories', component: CategoryListComponent }
    ]

  },


];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientRoutingModule { }
