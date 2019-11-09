import { Client } from './Client';
import { OrderLine } from './OrderLine';

export class Order {
	id:number;
	deliveryDate:number;
	tax:number;
	subtotal: number;
	totalPrice: number;
	ipAddress:string;
	client:Client;
	status:string;
	paymentMethod:string;
	orderLines:OrderLine[];
}