import { Product } from './Product';
import { Order } from './Order';

export class  OrderLine {  
    id:number;  
    product:Product;
    quantity:number = 1;
    price:number;
    totalPrice:number;
    order:Order;
}