import { Component, input } from '@angular/core';
import { Router, RouterLink } from "@angular/router";
import { AuthService } from '../../../service/auth/auth.service';
import { UserModel } from '../../../model/user/user.model';
import { UserService } from '../../../service/user/user.service';
import { EntityDialogService } from '../../../service/dialog/entity/entity-dialog.service';
import { DialogDataModel } from '../../model/dialog/dialog-data.model';
import { UserDetail } from '../../../feature/user/detail/user-detail';
import { UserCreateEdit } from '../../../feature/user/create-edit/user-create-edit';
import { Button } from "../button/button";
import { DetailDialogDataModel } from '../../model/dialog/detail-dialog-data.model';
import { getRoleConfig } from '../../config/role-config';
import { DialogRef } from '@angular/cdk/dialog';
import { BaseDialog } from '../dialog/base/base-dialog';
import { finalize } from 'rxjs';
import { SpinnerService } from '../../../service/ui/spinner/spinner.service';

@Component({
  selector: 'app-sidebar',
  imports: [RouterLink, Button],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar {
  readonly routes: SidebarRoute[] = [
    { path: '/', label: 'Home', icon: 'fa-house' },
    { path: '/movie', label: 'Movie', icon: 'fa-film' },
    { path: '/schedule', label: 'Schedule', icon: 'fa-calendar' },
    { path: '/room', label: 'Room', icon: 'fa-door-closed' },
    { path: '/ticket', label: 'Ticket', icon: 'fa-ticket', roles: ['ADMIN'] },
    { path: '/promotion', label: 'Promotion', icon: 'fa-percent' },
    { path: '/user', label: 'User', icon: 'fa-users', roles: ['ADMIN'] }
  ]
  visible = input<boolean>();

  fullname: string = '';
  email: string = '';

  constructor(
    private readonly authService: AuthService,
    private readonly userService: UserService,
    private readonly entityDialog: EntityDialogService,
    private readonly spinner: SpinnerService,
    private readonly router: Router
  ) {
    this.fullname = this.authService.getFullname();
    this.email = this.authService.getEmail();
  }

  onLogout(): void {
    this.authService.logout();
  }

  isActive(path: string): boolean {
    return this.router.url === path;
  }

  viewProfile(): void {
    this.spinner.show();

    this.userService.getById(this.authService.getId())
      .pipe(finalize(() => this.spinner.hide()))
      .subscribe((user) => {
        const dialogData: DetailDialogDataModel<UserModel> = {
          title: 'Profile',
          model: user,
          roleConfig: {
            ...getRoleConfig('user'),
            delete: []
          },
          openEdit: () => this.openEditProfile(user, dialogRef)
        }

        const dialogRef = this.entityDialog.openDetail(UserDetail, dialogData);
      });
  }

  private openEditProfile(user: UserModel, parentRef: DialogRef<unknown, BaseDialog>) {
    const dialogData: DialogDataModel<UserModel> = {
      title: 'Edit Profile',
      model: user,
    }

    const dialogRef = this.entityDialog.openForm(UserCreateEdit, dialogData, (form) => {
      this.spinner.show();

      this.userService.update(user.id, form.value)
        .pipe(finalize(() => this.spinner.hide()))
        .subscribe(() => {
          dialogRef.close();
        });
    });

    dialogRef.closed.subscribe(() => {
      parentRef.close();
      this.viewProfile();
    });
  }

  canShowRoute(roles: string[] | undefined): boolean {
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
  roles?: string[];
}
