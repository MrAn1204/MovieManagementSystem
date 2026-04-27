import { Injectable, Type } from '@angular/core';
import { DialogService } from '../dialog.service';
import { DialogDataModel } from '../../../shared/model/dialog/dialog-data.model';
import { BaseEntityModel } from '../../../shared/model/base-entity.model';
import { filter, takeUntil } from 'rxjs';
import { BaseDialog } from '../../../shared/component/dialog/base/base-dialog';
import { PopupModal } from '../../../shared/component/dialog/popup-modal/popup-modal';
import { DialogPopupDataModel } from '../../../shared/model/dialog/dialog-popup-data.model';
import { FormGroup } from '@angular/forms';
import { DetailDialogDataModel } from '../../../shared/model/dialog/detail-dialog-data.model';

@Injectable({
  providedIn: 'root',
})
export class EntityDialogService {
  constructor(private readonly dialogService: DialogService) { }

  openDetail<T extends BaseEntityModel>(dialog: Type<BaseDialog>, dialogData: DetailDialogDataModel<T>, reloadHandler?: () => void) {
    const ref = this.dialogService.openDialog(dialog, dialogData);

    ref.componentInstance?.dialogService.reload$
      .pipe(
        filter((event) => event.sourceRef === ref),
        takeUntil(ref.closed))
      .subscribe(() => {
        reloadHandler?.();
      });

    return ref;
  }

  openForm<T extends BaseEntityModel>(
    dialog: Type<BaseDialog>, dialogData: DialogDataModel<T>, saveHandler: (form: FormGroup) => void) {
    const dialogRef = this.dialogService.openDialog(dialog, dialogData);

    dialogRef.componentInstance?.dialogService.saveForm$
      .pipe(takeUntil(dialogRef.closed))
      .subscribe((form) => {
        if (form.valid) {
          saveHandler(form);
        }
      });

    return dialogRef;
  }

  openPopup(dialogData: DialogPopupDataModel, confirmHandler?: () => void) {
    const dialogRef = this.dialogService.openDialog(PopupModal, dialogData);

    dialogRef.componentInstance?.dialogService.confirmTask$
      .pipe(filter((event) => event.sourceRef === dialogRef))
      .subscribe(() => {
        confirmHandler?.();
      });

    return dialogRef;
  }
}
