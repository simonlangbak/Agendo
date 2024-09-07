import { Route } from '@angular/router';
import { LoginComponent } from './core/login/login.component';
import { MainComponent } from './core/main/main.component';
import { AuthGuard } from './core/auth/auth.guard';
import { TodoViewComponent } from './feature/todo-view/todo-view.component';

export const appRoutes: Route[] = [
    {
        title: "Home - Agendo",
        path: '',
        component: MainComponent,
        canActivate: [AuthGuard],
        children: [
            {
                path: '',
                component: TodoViewComponent,
                outlet: 'main-view'
            },
        ]
    },
    {
        title: "Login - Agendo",
        path: 'login',
        component: LoginComponent
    },

    // Otherwise redirect to home
    {
        path: '**',
        redirectTo: ''
    }
];
