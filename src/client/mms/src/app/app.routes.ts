import { Routes } from '@angular/router';
import { Home } from './feature/home/home';
import { Movie } from './feature/movie/component/movie';
import { Login } from './feature/auth/login/login';
import { MainLayout } from './shared/main-layout/main-layout';

export const routes: Routes = [
  {
    path: 'login',
    component: Login,
    title: 'Login',
  },
  {
    path: '',
    component: MainLayout,
    children: [
      {
        path: '',
        component: Home,
        title: 'Home page',
      },
      {
        path: 'movie',
        component: Movie,
        title: 'Movie',
      },
    ]
  },
];
