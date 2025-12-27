import { Routes } from '@angular/router';
import { Home } from './feature/home/home';
import { Movie } from './feature/movie/component/movie';

export const routes: Routes = [
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
];
