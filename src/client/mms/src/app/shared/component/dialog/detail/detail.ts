import { DIALOG_DATA } from '@angular/cdk/dialog';
import { Component, inject } from '@angular/core';
import { BaseDialog } from '../base/base-dialog';
import { NgComponentOutlet } from '@angular/common';
import { DetailButtons } from "../../detail/detail-buttons/detail-buttons";
import { DialogDataModel } from '../../../model/dialog/dialog-data.model';
import { CreateEdit } from '../create-edit/create-edit';
import { PopupModal } from '../popup-modal/popup-modal';
import { AuthService } from '../../../../service/auth/auth.service';

@Component({
  selector: 'app-detail',
  imports: [NgComponentOutlet, DetailButtons],
  templateUrl: './detail.html',
  styleUrl: './detail.css',
})
export class Detail extends BaseDialog {
  data: DialogDataModel = inject(DIALOG_DATA);

  constructor(private readonly authService: AuthService) {
    super();
  }

  openUpdate = () => this.dialogService.triggerOpen(CreateEdit);

  openDelete = () => this.dialogService.triggerOpen(PopupModal);

  canEdit(): boolean {
    return this.authService.includeRoles(this.data.roleConfig?.edit ?? []);
  }

  canDelete(): boolean {
    return this.authService.includeRoles(this.data.roleConfig?.delete ?? []);
  }
}
