import { Routes } from '@angular/router';
import { Home } from './feature/home/home';
import { Movie } from './feature/movie/component/movie';
import { Schedule } from './feature/schedule/component/schedule';
import { Login } from './feature/auth/login/login';
import { authGuard } from './guard/auth/auth-guard';
import { MainLayout } from './shared/main-layout/main-layout';
import { Register } from './feature/auth/register/register';
import { Room } from './feature/room/component/room';
import { User } from './feature/user/component/user';
import { Promotion } from './feature/promotion/component/promotion';

export const routes: Routes = [
  {
    path: 'login',
    component: Login,
    title: 'Login',
  },
  {
    path: 'register',
    component: Register,
    title: 'Register',
  },
  {
    path: '',
    component: MainLayout,
    canActivate: [authGuard],
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
      {
        path: 'schedule',
        component: Schedule,
        title: 'Schedule',
      },
      {
        path: 'room',
        component: Room,
        title: 'Room',
      },
      {
        path: 'promotion',
        component: Promotion,
        title: 'Promotion',
      },
      {
        path: 'user',
        component: User,
        title: 'User',
      },
    ]
  },
];
