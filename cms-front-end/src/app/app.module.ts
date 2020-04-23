import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SideBarComponent } from './_components/main/side-bar/side-bar.component';
import { HeaderComponent } from './_components/header/header.component';
import { ProductComponent } from './_components/main/product/product.component';
import { ProductListComponent } from './_components/main/product/product-list/product-list.component';
import { CategoryComponent } from './_components/main/category/category.component';
import { CategoryListComponent } from './_components/main/category/category-list/category-list.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CategoryEditComponent } from './_components/main/category/category-edit/category-edit.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { ProductEditComponent } from './_components/main/product/product-edit/product-edit.component';
import { DatePipe } from '@angular/common';
import { UserComponent } from './_components/main/user/user.component';
import { UserListComponent } from './_components/main/user/user-list/user-list.component';
import { UserEditComponent } from './_components/main/user/user-edit/user-edit.component';
import { BasicAuthInterceptor } from './_interceptors/basic-auth.interceptor';
import { LoginComponent } from './_components/login/login.component';
import { DashBoardComponent } from './_components/main/dash-board/dash-board.component';
import { MainComponent } from './_components/main/main.component';
import { OrderComponent } from './_components/main/order/order.component';
import { OrderListComponent } from './_components/main/order/order-list/order-list.component';
import { OrderEditComponent } from './_components/main/order/order-edit/order-edit.component';
import { AuthGuard } from './_guards/auth.guard';
import { ProfileComponent } from './_components/main/profile/profile.component';
import { AdminGuard } from './_guards/admin.guard';

@NgModule({
  declarations: [
    AppComponent,
    SideBarComponent,
    HeaderComponent,
    ProductComponent,
    ProductListComponent,
    CategoryComponent,
    CategoryListComponent,
    CategoryEditComponent,
    ProductEditComponent,
    OrderComponent,
    OrderListComponent,
    OrderEditComponent,
    UserComponent,
    UserListComponent,
    UserEditComponent,
    LoginComponent,
    DashBoardComponent,
    MainComponent,
    ProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AngularFontAwesomeModule
  ],
  providers: [
    DatePipe,
    AuthGuard,
    AdminGuard,
    { provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
