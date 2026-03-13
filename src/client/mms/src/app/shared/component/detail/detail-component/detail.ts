import { Component, input, output } from '@angular/core';
import { DetailButtons } from "../detail-buttons/detail-buttons";
import { AuthService } from '../../../../service/auth/auth.service';
import { RoleConfigModel } from '../../../model/role-config.model';

@Component({
  selector: 'app-detail',
  imports: [DetailButtons],
  templateUrl: './detail.html',
  styleUrl: './detail.css',
})
export class Detail {
  title = input<string>();

  roleConfig = input<RoleConfigModel | undefined>(undefined);

  constructor(private readonly authService: AuthService) {
  }

  openUpdate = output<void>();

  openDelete = output<void>();

  closeDialog = output<void>();

  canEdit(): boolean {
    return this.authService.includeRoles(this.roleConfig()?.edit ?? []);
  }

  canDelete(): boolean {
    return this.authService.includeRoles(this.roleConfig()?.delete ?? []);
  }
}
