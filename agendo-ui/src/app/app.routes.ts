import { Route } from '@angular/router';
import { LoginComponent } from './core/login/login.component';
import { MainComponent } from './core/main/main.component';
import { AuthGuard } from './core/auth/auth.guard';

export const appRoutes: Route[] = [
    { path: '', component: MainComponent, title: "Home - Agendo", canActivate: [AuthGuard]},
    { path: 'login', component: LoginComponent, title: "Login - Agendo" },

    // Otherwise redirect to home
    { path: '**', redirectTo: '' }
];
