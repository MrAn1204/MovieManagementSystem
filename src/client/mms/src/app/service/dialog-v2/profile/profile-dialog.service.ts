import { Injectable } from '@angular/core';
import { DialogServiceV2 } from '../dialog.service';
import { ProfileDetail } from '../../../feature/user-v2/profile-detail/profile-detail';

@Injectable({
  providedIn: 'root',
})
export class ProfileDialogService extends DialogServiceV2 {
  displayDetail() {
    const dialogRef = this.dialog.open(ProfileDetail, {
      width: '800px',
      maxWidth: '90vw',
      minWidth: '0',
    });

    return dialogRef.afterClosed();
  }
}
