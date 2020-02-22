import { Authority } from './Authority';
import { Response } from './Response';

export class User {
    id: number;
    email: string;
    password: string;
    name: string;
    phone: string;
    active: boolean;
    registerDate: string;
    authority: Authority = new Authority();
}