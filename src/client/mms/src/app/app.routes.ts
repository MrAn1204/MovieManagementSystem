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
import { Ticket } from './feature/ticket/component/ticket';
import { NotFoundPage } from './feature/error/not-found-page/not-found-page';
import { ServerErrorPage } from './feature/error/server-error-page/server-error-page';
import { ForgotPassword } from './feature/auth/forgot-password/forgot-password';
import { ResetPassword } from './feature/auth/reset-password/reset-password';
import { passwordResetGuard } from './guard/password/password-reset-guard';
import { MainLayoutV2 } from './shared/main-layout-v2/main-layout-v2';
import { MovieV2 } from './feature/movie-v2/component/movie';
import { HomeV2 } from './feature/home-v2/home-v2';
import { ScheduleV2 } from './feature/schedule-v2/component/schedule-v2';
import { RoomV2 } from './feature/room-v2/component/room-v2';
import { PromotionV2 } from './feature/promotion-v2/component/promotion-v2';
import { TicketV2 } from './feature/ticket-v2/component/ticket-v2';
import { UserV2 } from './feature/user-v2/component/user-v2';
import { LoginV2 } from './feature/auth-v2/login/login-v2';
import { RegisterV2 } from './feature/auth-v2/register/register-v2';
import { ForgotPasswordV2 } from './feature/auth-v2/forgot-password/forgot-password-v2';
import { ResetPasswordV2 } from './feature/auth-v2/reset-password/reset-password-v2';

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
    path: 'forgot-password',
    component: ForgotPassword,
    title: 'Forgot Password',
  },
  {
    path: 'reset-password',
    component: ResetPassword,
    title: 'Reset Password',
    canActivate: [passwordResetGuard],
  },
  {
    path: 'v2/login',
    component: LoginV2,
    title: 'Login',
  },
  {
    path: 'v2/register',
    component: RegisterV2,
    title: 'Register',
  },
  {
    path: 'v2/forgot-password',
    component: ForgotPasswordV2,
    title: 'Forgot Password',
  },
  {
    path: 'v2/reset-password',
    component: ResetPasswordV2,
    title: 'Reset Password',
    canActivate: [passwordResetGuard],
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
        path: 'ticket',
        component: Ticket,
        title: 'Ticket',
      },
      {
        path: 'user',
        component: User,
        title: 'User',
      },
    ]
  },
  {
    path: 'v2',
    component: MainLayoutV2,
    canActivate: [authGuard],
    children: [
      {
        path: '',
        component: HomeV2,
        title: 'Home',
      },
      {
        path: 'movie',
        component: MovieV2,
        title: 'Movie',
      },
      {
        path: 'schedule',
        component: ScheduleV2,
        title: 'Schedule',
      },
      {
        path: 'room',
        component: RoomV2,
        title: 'Room',
      },
      {
        path: 'promotion',
        component: PromotionV2,
        title: 'Promotion',
      },
      {
        path: 'ticket',
        component: TicketV2,
        title: 'Ticket',
      },
      {
        path: 'user',
        component: UserV2,
        title: 'User',
      },
    ]
  },
  {
    path: 'not-found',
    component: NotFoundPage,
    title: 'Not Found',
  },
  {
    path: 'error',
    component: ServerErrorPage,
    title: 'Error',
  },
  {
    path: '**',
    redirectTo: 'not-found',
  }
];
