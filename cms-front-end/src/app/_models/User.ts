import { Authority } from './Authority';

export class User {
    id: number;
    email: string;
    password: string;
    name: string;
    phone: string;
    state: boolean;
    registerDate: string;
    authority: Authority = new Authority();
}