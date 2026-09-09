import { Component, output } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ButtonIcon } from "../button-icon/button-icon";
import { AuthService } from '../../../service/auth/auth.service';
import { ProfileDialogService } from '../../../service/dialog-v2/profile/profile-dialog.service';
import { ButtonV2 } from "../button/button";
import { MatSnackBar } from '@angular/material/snack-bar';
import { Menu, MenuItem } from "../menu/menu";
import { MatMenuModule } from "@angular/material/menu";
import { NotificationDialogService } from '../../../service/dialog-v2/notification/notification-dialog.service';

@Component({
  selector: 'app-header-v2',
  imports: [MatToolbarModule, MatIconModule, ButtonIcon, ButtonV2, Menu, MatMenuModule],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class HeaderV2 {
  toggleSidebar = output<void>();

  fullname: string = '';
  email: string = '';

  menu: MenuItem[] = [
    { label: 'Profile', action: 'profile', icon: 'person' },
    { label: 'Logout', action: 'logout', icon: 'logout' },
  ];

  constructor(
    private readonly authService: AuthService,
    private readonly notification: NotificationDialogService,
    private readonly snackbar: MatSnackBar,
    private readonly profileDialog: ProfileDialogService,
  ) {
    this.fullname = this.authService.getFullname();
    this.email = this.authService.getEmail();
  }

  onLogout(): void {
    const ref = this.notification.openDialog({
      type: 'warning',
      message: 'Are you sure you want to logout?',
    });

    ref.afterClosed().subscribe((result) => result && this.authService.logout());
  }

  handleMenuAction(action: string): void {
    if (action === 'profile') {
      this.displayProfile();
    } else if (action === 'logout') {
      this.onLogout();
    }
  }

  displayProfile(): void {
    this.profileDialog.displayDetail().subscribe((res) => {
      if (res === 'refresh') {
        this.displayProfile();
      } else if (res === 'edit') {
        this.displayEditProfile();
      } else if (res === 'password') {
        this.displayChangePassword();
      }
    });
  }

  displayEditProfile(): void {
    this.profileDialog.displayEdit().subscribe((res) => res && this.snackbar.open(
      'Your profile has been updated successfully.',
      'Close',
      { duration: 5000 }
    ));
  }

  displayChangePassword(): void {
    this.profileDialog.displayChangePassword().subscribe((res) => res && this.authService.forgotPassword(this.email)
      .subscribe(() => this.snackbar.open(
        'A link has been sent to your email to change your password. Please check your inbox.',
        'Close',
        { duration: 5000 }
      ))
    );
  }
}
