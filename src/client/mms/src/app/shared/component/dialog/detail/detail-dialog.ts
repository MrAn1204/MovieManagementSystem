import { Component, inject, Type } from '@angular/core';
import { BaseDialog } from '../base/base-dialog';
import { DialogDataModel } from '../../../model/dialog/dialog-data.model';
import { DIALOG_DATA } from '@angular/cdk/dialog';
import { BaseEntityModel } from '../../../model/base-entity.model';
import { PopupModal } from '../popup-modal/popup-modal';

@Component({
  selector: 'app-detail-dialog',
  imports: [],
  templateUrl: './detail-dialog.html',
  styleUrl: './detail-dialog.css',
})
export abstract class DetailDialog<T extends BaseEntityModel> extends BaseDialog {
  data: DialogDataModel<T> = inject(DIALOG_DATA);

  protected abstract readonly updateDialog: Type<BaseDialog>;

  get model(): T | undefined {
    return this.data.model;
  }

  openUpdate(): void {
    this.dialogService.triggerOpen(this.updateDialog);
  }

  openDelete(): void {
    this.dialogService.triggerOpen(PopupModal);
  }
}
