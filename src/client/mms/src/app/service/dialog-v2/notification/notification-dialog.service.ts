import { Injectable } from '@angular/core';
import { DialogServiceV2 } from '../dialog.service';
import { NotificationDialogDataModel } from '../../../shared/model/dialog/notification-dialog-data.model';
import { NotificationDialog } from '../../../shared/component-v2/dialog/notification-dialog/notification-dialog';

@Injectable({
  providedIn: 'root',
})
export class NotificationDialogService extends DialogServiceV2 {
  openDialog(dialogData: NotificationDialogDataModel) {
    const dialogRef = this.dialog.open(NotificationDialog, {
      data: dialogData,
      maxWidth: '90vw',
      minWidth: '0',
    });

    return dialogRef;
  }
}
