import { Component } from '@angular/core';
import { Router, RouterLink } from "@angular/router";
import { RoleName } from '../../model/role-config.model';
import { AuthService } from '../../../service/auth/auth.service';
import { MatListModule } from '@angular/material/list';
import { MatIcon } from "@angular/material/icon";

@Component({
  selector: 'app-sidebar-v2',
  imports: [RouterLink, MatListModule, MatIcon],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class SidebarV2 {
  readonly routes: SidebarRoute[] = [
    { path: '/v2', label: 'Home', icon: 'home' },
    { path: '/v2/movie', label: 'Movie', icon: 'movie' },
    { path: '/v2/schedule', label: 'Schedule', icon: 'calendar_today' },
    { path: '/v2/room', label: 'Room', icon: 'meeting_room' },
    { path: '/v2/ticket', label: 'Ticket', icon: 'local_activity', roles: ['ADMIN'] },
    { path: '/v2/promotion', label: 'Promotion', icon: 'percent_discount' },
    { path: '/v2/user', label: 'User', icon: 'groups', roles: ['ADMIN'] }
  ]

  fullname: string = '';
  email: string = '';

  constructor(
    private readonly authService: AuthService,
    private readonly router: Router
  ) {
    this.fullname = this.authService.getFullname();
    this.email = this.authService.getEmail();
  }

  isActive(path: string): boolean {
    return this.router.url === path;
  }

  canShowRoute(roles: RoleName[] | undefined): boolean {
    if (!roles) {
      return true;
    }

    return this.authService.includeRoles(roles);
  }
}

interface SidebarRoute {
  path: string;
  label: string;
  icon: string;
  roles?: RoleName[];
}
