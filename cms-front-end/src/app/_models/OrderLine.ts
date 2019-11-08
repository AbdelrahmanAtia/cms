import { Product } from './Product';

export class  OrderLine {    
    product:Product;
    quantity:number = 1;
    price:number;
    totalPrice:number;
}