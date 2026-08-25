import { Type, inject } from "@angular/core";
import { BaseDialogV2 } from "../../shared/component-v2/dialog/base-dialog/base-dialog";
import { BaseEntityModel } from "../../shared/model/base-entity.model";
import { DetailDialogDataModel } from "../../shared/model/dialog/detail-dialog-data.model";
import { DialogFormDataModel } from "../../shared/model/dialog/dialog-form-data.model";
import { NotificationDialogDataModel } from "../../shared/model/dialog/notification-dialog-data.model";
import { DialogServiceV2 } from "./dialog.service";
import { NotificationDialogService } from "./notification/notification-dialog.service";
import { finalize, Observable, switchMap, tap } from "rxjs";

export abstract class EntityDialogServiceV2<T extends BaseEntityModel> extends DialogServiceV2 {
  protected abstract entityName: string;

  protected abstract detailDialog: Type<BaseDialogV2>;
  protected abstract formDialog: Type<BaseDialogV2>;

  private readonly notification = inject(NotificationDialogService);

  private openDetail(dialogData: DetailDialogDataModel<T>) {
    const dialogRef = this.dialog.open(this.detailDialog, {
      data: dialogData,
      width: '800px',
      maxWidth: '90vw',
      minWidth: '0',
    });

    return dialogRef;
  }

  private openForm(dialogData: DialogFormDataModel<T>) {
    const dialogRef = this.dialog.open(this.formDialog, {
      data: dialogData,
      width: '1000px',
      maxWidth: '90vw',
      minWidth: '0',
    });

    return dialogRef;
  }

  displayInfo(id: string) {
    const dialogData: DetailDialogDataModel<T> = {
      title: `${this.entityName} Details`,
      id: id
    };

    const dialogRef = this.openDetail(dialogData);

    return dialogRef.afterClosed();
  }

  displayAdd(data?: Record<string, unknown>) {
    const dialogData: DialogFormDataModel<T> = {
      title: `Create ${this.entityName}`,
      ...data
    };

    const dialogRef = this.openForm(dialogData);

    return dialogRef.afterClosed().pipe(tap((res) => {
      if (res) {
        this.createSuccess();
      }
    }));
  }

  displayEdit(id: string, data?: Record<string, unknown>) {
    const dialogData: DialogFormDataModel<T> = {
      title: `Edit ${this.entityName}`,
      id: id,
      ...data
    };

    const dialogRef = this.openForm(dialogData);

    return dialogRef.afterClosed().pipe(tap((res) => {
      if (res) {
        this.updateSuccess();
      }
    }));
  }

  displayDelete(onConfirm: () => Observable<any>) {
    const lowercaseName = this.entityName.toLowerCase();

    const dialogData: NotificationDialogDataModel = {
      type: 'warning',
      message: `Are you sure you want to delete this ${lowercaseName}? This action cannot be undone.`,
    };

    const dialogRef = this.notification.openDialog(dialogData);

    return dialogRef.afterClosed().pipe(
      switchMap((res) => {
        if (res) {
          this.spinner.show();
          return onConfirm().pipe(
            tap(() => this.deleteSuccess()),
            finalize(() => this.spinner.hide())
          );
        }
        return res;
      })
    );
  }

  createSuccess() {
    this.snackbar.open(
      `The ${this.entityName.toLowerCase()} has been successfully created.`,
      'Close',
      { duration: 5000 });
  }

  updateSuccess() {
    this.snackbar.open(
      `The ${this.entityName.toLowerCase()} has been successfully updated.`,
      'Close',
      { duration: 5000 });
  }

  deleteSuccess() {
    this.snackbar.open(
      `The ${this.entityName.toLowerCase()} has been successfully deleted.`,
      'Close',
      { duration: 5000 });
  }
}
