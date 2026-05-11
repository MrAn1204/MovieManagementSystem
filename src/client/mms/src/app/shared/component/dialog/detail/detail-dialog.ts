import { Component, inject, Type } from '@angular/core';
import { BaseDialog } from '../base/base-dialog';
import { DIALOG_DATA } from '@angular/cdk/dialog';
import { BaseEntityModel } from '../../../model/base-entity.model';
import { DetailDialogDataModel } from '../../../model/dialog/detail-dialog-data.model';
import { AuditableEntityModel } from '../../../model/auditable-entity.model';

@Component({
  selector: 'app-detail-dialog',
  imports: [],
  templateUrl: './detail-dialog.html',
  styleUrl: './detail-dialog.css',
})
export abstract class DetailDialog<T extends (BaseEntityModel | AuditableEntityModel)> extends BaseDialog {
  data: DetailDialogDataModel<T> = inject(DIALOG_DATA);

  protected abstract readonly updateDialog: Type<BaseDialog>;

  get model(): T | undefined {
    return this.data.model;
  }

  get auditInfo() {
    if (!this.isAuditable()) {
      return null;
    }

    const model = this.model as AuditableEntityModel;

    return {
      createdAt: model.createdAt,
      updatedAt: model.updatedAt,
      createdBy: model.createdBy,
      updatedBy: model.updatedBy,
    };
  }

  private isAuditable() {
    return this.model && 'createdAt' in this.model && 'updatedAt' in this.model && 'createdBy' in this.model && 'updatedBy' in this.model;
  }

  openUpdate(): void {
    this.data.openEdit?.();
  }

  openDelete(): void {
    this.data.openDelete?.();
  }

  reload(): void {
    this.dialogService.triggerReload(this.dialogRef);
  }
}
