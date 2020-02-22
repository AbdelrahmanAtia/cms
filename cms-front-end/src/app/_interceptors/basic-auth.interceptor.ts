import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

export class BasicAuthInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        //don't add authorization header for login
        if (req.url.search('login') === -1) {
            req = req.clone({
                setHeaders: {
                    'Authorization': 'Basic ' + localStorage.getItem('currentUser')
                }
            });
        }
        return next.handle(req);
    }
}
