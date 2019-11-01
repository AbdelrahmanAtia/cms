import { Client } from './Client';

export class Order {
	id:number;
	deliveryDate:string;
	tax:number;
	subtotal: number;
	totalPrice: number;
	ipAddress:string;
	client:Client
}