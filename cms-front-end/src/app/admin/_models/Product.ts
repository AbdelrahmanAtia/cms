import { Category } from './Category';

export class Product {
    id: number;
    name: string;
    description: string;
    price: number;
    active: boolean;
    imageName: string;
    base64Image: string | ArrayBuffer;
    category: Category;
}