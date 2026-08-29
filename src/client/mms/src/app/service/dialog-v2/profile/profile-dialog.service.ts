import { inject, Injectable } from '@angular/core';
import { DialogServiceV2 } from '../dialog.service';
import { ProfileDetail } from '../../../feature/user-v2/profile-detail/profile-detail';
import { ProfileEdit } from '../../../feature/user-v2/profile-edit/profile-edit';
import { NotificationDialogService } from '../notification/notification-dialog.service';

@Injectable({
  providedIn: 'root',
})
export class ProfileDialogService extends DialogServiceV2 {
  private readonly notification = inject(NotificationDialogService);

  displayDetail() {
    const dialogRef = this.dialog.open(ProfileDetail, {
      width: '800px',
      maxWidth: '90vw',
      minWidth: '0',
    });

    return dialogRef.afterClosed();
  }

  displayEdit() {
    const dialogRef = this.dialog.open(ProfileEdit, {
      width: '1000px',
      maxWidth: '90vw',
      minWidth: '0',
    });

    return dialogRef.afterClosed();
  }

  displayChangePassword() {
    const dialogRef = this.notification.openDialog({
      type: 'warning',
      message: 'A link will be sent to your email to change your password. Are you sure you want to continue?',
    });

    return dialogRef.afterClosed();
  }
}
