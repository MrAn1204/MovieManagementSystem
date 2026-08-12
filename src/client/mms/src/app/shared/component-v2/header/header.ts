import { Component, output } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ButtonIcon } from "../button-icon/button-icon";
import { AuthService } from '../../../service/auth/auth.service';
import { UserService } from '../../../service/user/user.service';
import { DialogServiceV2 } from '../../../service/dialog-v2/dialog.service';
import { UserDetailModel } from '../../../model/user/user-detail.model';

@Component({
  selector: 'app-header-v2',
  imports: [MatToolbarModule, MatIconModule, ButtonIcon],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class HeaderV2 {
  toggleSidebar = output<void>();

  fullname: string = '';
  email: string = '';

  constructor(
    private readonly authService: AuthService,
    private readonly userService: UserService,
  ) {
    this.fullname = this.authService.getFullname();
    this.email = this.authService.getEmail();
  }

  onLogout(): void {
    this.authService.logout();
  }

  showUserInfo(): void {
    this.userService.getById(this.authService.getId()).subscribe((res) => this.displayProfile(res));
  }

  private displayProfile(user: UserDetailModel): void {
    // TODO: Display user profile in a dialog
  }
}
