import { Category } from './Category';

export class Product {
    id: number;
    name: string;
    description: string;
    price: number;
    active: boolean;
    image: string | ArrayBuffer;
    category:Category;
    
}