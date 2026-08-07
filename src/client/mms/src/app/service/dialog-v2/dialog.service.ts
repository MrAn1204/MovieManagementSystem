import { Injectable, Type } from '@angular/core';
import { BaseEntityModel } from '../../shared/model/base-entity.model';
import { BaseDialogV2 } from '../../shared/component-v2/dialog/base-dialog/base-dialog';
import { DetailDialogDataModel } from '../../shared/model/dialog/detail-dialog-data.model';
import { DialogFormDataModel } from '../../shared/model/dialog/dialog-form-data.model';
import { NotificationDialogDataModel } from '../../shared/model/dialog/notification-dialog-data.model';
import { NotificationDialog } from '../../shared/component-v2/dialog/notification-dialog/notification-dialog';
import { MatDialog } from '@angular/material/dialog';

@Injectable({
  providedIn: 'root',
})
export class DialogServiceV2 {
  constructor(private readonly dialog: MatDialog) { }

  openDetail<T extends BaseEntityModel>(dialogComponent: Type<BaseDialogV2>, dialogData: DetailDialogDataModel<T>) {
    const dialogRef = this.dialog.open(dialogComponent, {
      data: dialogData,
      width: '800px',
      maxWidth: '90vw',
      minWidth: '0',
    });

    return dialogRef;
  }

  openForm<T extends BaseEntityModel>(dialogComponent: Type<BaseDialogV2>, dialogData: DialogFormDataModel<T>) {
    const dialogRef = this.dialog.open(dialogComponent, {
      data: dialogData,
      width: '1000px',
      maxWidth: '90vw',
      minWidth: '0',
    });

    return dialogRef;
  }

  openNotification(dialogData: NotificationDialogDataModel) {
    const dialogRef = this.dialog.open(NotificationDialog, {
      data: dialogData,
      maxWidth: '90vw',
      minWidth: '0',
    });
    return dialogRef;
  }
}
