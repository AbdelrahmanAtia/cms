import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Router } from '@angular/router';

export class BasicAuthInterceptor implements HttpInterceptor {

    constructor(private router: Router) {}

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

        //this code is used to do some action based
        //on response headers..
        /*
        return next.handle(req).pipe(
            tap(evt => {

            }),
            catchError((error: any) => {

                if(error instanceof HttpErrorResponse){
                    if(error.status == 403){
                        console.log('redirect to access denied');
                        window.location.href = "assets/accessDenied.html";
                    }

                    if(error.status == 401){
                        console.log('redirect to login');
                    }
                }
                return of(error);
            })
        );
        */
    }
}
