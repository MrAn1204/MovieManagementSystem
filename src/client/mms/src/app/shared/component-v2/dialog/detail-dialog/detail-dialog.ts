import { Component, inject } from '@angular/core';
import { BaseDialogV2 } from '../base-dialog/base-dialog';
import { DetailDialogDataModel } from '../../../model/dialog/detail-dialog-data.model';
import { BaseEntityModel } from '../../../model/base-entity.model';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-detail-dialog',
  imports: [],
  templateUrl: './detail-dialog.html',
  styleUrl: './detail-dialog.css',
})
export abstract class DetailDialogV2<T extends BaseEntityModel> extends BaseDialogV2 {
  data: DetailDialogDataModel<T> = inject(MAT_DIALOG_DATA);

  get model(): T | undefined {
    return this.data.model;
  }

  openUpdate(): void {
    this.onClose('edit');
  }

  openDelete(): void {
    this.onClose('delete');
  }
}
