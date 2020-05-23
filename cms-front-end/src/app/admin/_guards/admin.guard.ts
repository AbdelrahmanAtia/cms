import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthenticationService } from '../_services/authentication.service';

export class AdminGuard implements CanActivate {

    constructor(private authenticationService: AuthenticationService) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        let currentUserAuthority: string = this.authenticationService.getCurrentUserAuthority();        
        if (currentUserAuthority == 'Administrator') {
            //admin, so return true
            return true;
        }
        //not admin so redirect to access denied page 
        window.location.href = "assets/accessDenied.html";
        return false;
    }
}