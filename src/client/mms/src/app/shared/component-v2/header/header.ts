import { Component, output } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ButtonIcon } from "../button-icon/button-icon";
import { AuthService } from '../../../service/auth/auth.service';
import { ProfileDialogService } from '../../../service/dialog-v2/profile/profile-dialog.service';
import { ButtonV2 } from "../button/button";

@Component({
  selector: 'app-header-v2',
  imports: [MatToolbarModule, MatIconModule, ButtonIcon, ButtonV2],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class HeaderV2 {
  toggleSidebar = output<void>();

  fullname: string = '';
  email: string = '';

  constructor(
    private readonly authService: AuthService,
    private readonly profileDialog: ProfileDialogService
  ) {
    this.fullname = this.authService.getFullname();
    this.email = this.authService.getEmail();
  }

  onLogout(): void {
    this.authService.logout();
  }

  displayProfile(): void {
    this.profileDialog.displayDetail().subscribe((res) => res === 'refresh' && this.displayProfile());
  }
}
