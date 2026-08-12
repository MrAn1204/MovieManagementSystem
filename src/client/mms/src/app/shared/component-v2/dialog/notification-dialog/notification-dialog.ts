import { Component, inject } from '@angular/core';
import { BaseDialogV2 } from '../base-dialog/base-dialog';
import { NotificationContainer } from "../notification-container/notification-container";
import { NotificationDialogDataModel, NotificationType } from '../../../model/dialog/notification-dialog-data.model';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-notification-dialog',
  imports: [NotificationContainer],
  templateUrl: './notification-dialog.html',
  styleUrl: './notification-dialog.css',
})
export class NotificationDialog extends BaseDialogV2 {
  private readonly data = inject<NotificationDialogDataModel>(MAT_DIALOG_DATA);

  get type(): NotificationType {
    return this.data.type;
  }

  get message(): string {
    return this.data.message;
  }

  onConfirm() {
    this.data.onConfirm?.().subscribe({
      next: () => this.onClose(true),
      error: () => this.onClose(),
    });
  }
}
