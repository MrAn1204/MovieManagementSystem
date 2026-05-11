import { Component, input, output } from '@angular/core';
import { DialogButtons } from "../../dialog/dialog-buttons/dialog-buttons";
import { AuthService } from '../../../../service/auth/auth.service';
import { RoleConfigModel } from '../../../model/role-config.model';
import { Button } from "../../button/button";
import { FormatCellPipe } from '../../../pipe/format-cell/format-cell-pipe';

@Component({
  selector: 'app-detail',
  imports: [DialogButtons, Button, FormatCellPipe],
  templateUrl: './detail.html',
  styleUrl: './detail.css',
})
export class Detail {
  title = input<string>();

  roleConfig = input<RoleConfigModel | undefined>(undefined);

  auditInfo = input<{
    createdAt: string;
    updatedAt: string;
    createdBy: string;
    updatedBy: string;
  } | null>(null);

  constructor(private readonly authService: AuthService) {
  }

  openUpdate = output<void>();

  openDelete = output<void>();

  closeDialog = output<void>();

  canSeeAuditInfo(): boolean {
    return this.authService.includeRoles(['ADMIN']);
  }

  canEdit(): boolean {
    return this.authService.includeRoles(this.roleConfig()?.edit ?? []);
  }

  canDelete(): boolean {
    return this.authService.includeRoles(this.roleConfig()?.delete ?? []);
  }
}
