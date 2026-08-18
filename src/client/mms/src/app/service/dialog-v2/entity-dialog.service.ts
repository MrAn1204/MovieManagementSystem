import { Type, inject } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { finalize, switchMap, tap } from "rxjs";
import { BaseDialogV2 } from "../../shared/component-v2/dialog/base-dialog/base-dialog";
import { BaseEntityModel } from "../../shared/model/base-entity.model";
import { DetailDialogDataModel } from "../../shared/model/dialog/detail-dialog-data.model";
import { DialogFormDataModel } from "../../shared/model/dialog/dialog-form-data.model";
import { NotificationDialogDataModel } from "../../shared/model/dialog/notification-dialog-data.model";
import { EntityService } from "../entity.service";
import { DialogServiceV2 } from "./dialog.service";
import { NotificationDialogService } from "./notification/notification-dialog.service";

export abstract class EntityDialogServiceV2<T extends BaseEntityModel> extends DialogServiceV2 {
  protected abstract entityName: string;
  protected abstract entityService: EntityService<T>;

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

  showDetailDialog(id: string) {
    this.spinner.show();

    return this.entityService.getById(id).pipe(
      finalize(() => this.spinner.hide()),
      switchMap(res => this.displayInfo(res))
    );
  }

  showAddEditDialog(id?: string, data?: Record<string, unknown>) {
    if (id) {
      this.spinner.show();

      return this.entityService.getById(id)
        .pipe(
          finalize(() => this.spinner.hide()),
          switchMap(res => this.displayEdit(res, data))
        );
    } else {
      return this.displayAdd(data);
    }
  }

  showDeleteDialog(id: string) {
    return this.displayDelete(id);
  }

  protected displayInfo(item: T) {
    const dialogData: DetailDialogDataModel<T> = {
      title: `${this.entityName} Details`,
      model: item,
    };

    const dialogRef = this.openDetail(dialogData);

    return dialogRef.afterClosed();
  }

  protected displayAdd(data?: Record<string, unknown>) {
    const dialogData: DialogFormDataModel<T> = {
      title: `Create ${this.entityName}`,
      onSubmit: (form) => this.saveNew(form),
      ...data
    };

    const dialogRef = this.openForm(dialogData);

    return dialogRef.afterClosed();
  }

  protected displayEdit(item: T, data?: Record<string, unknown>) {
    const dialogData: DialogFormDataModel<T> = {
      title: `Edit ${this.entityName}`,
      model: item,
      onSubmit: (form) => this.saveUpdate(item.id, form),
      ...data
    };

    const dialogRef = this.openForm(dialogData);

    return dialogRef.afterClosed();
  }

  protected displayDelete(id: string) {
    const lowercaseName = this.entityName.toLowerCase();

    const dialogData: NotificationDialogDataModel = {
      type: 'warning',
      message: `Are you sure you want to delete this ${lowercaseName}? This action cannot be undone.`,
      onConfirm: () => this.confirmDelete(id)
    };

    const dialogRef = this.notification.openDialog(dialogData);

    return dialogRef.afterClosed();
  }

  protected saveNew(form: FormGroup, onClose?: () => void) {
    this.spinner.show();

    return this.entityService.create(form.getRawValue()).pipe(
      tap(() => this.createSuccess()),
      finalize(() => {
        this.spinner.hide();
        onClose?.();
      })
    );
  }

  protected createSuccess() {
    this.snackbar.open(
      `The ${this.entityName.toLowerCase()} has been successfully created.`,
      'Close',
      { duration: 5000 });
  }

  protected saveUpdate(id: string, form: FormGroup, onClose?: () => void) {
    this.spinner.show();

    return this.entityService.update(id, form.getRawValue()).pipe(
      tap(() => this.updateSuccess()),
      finalize(() => {
        this.spinner.hide();
        onClose?.();
      })
    );
  }

  protected updateSuccess() {
    this.snackbar.open(
      `The ${this.entityName.toLowerCase()} has been successfully updated.`,
      'Close',
      { duration: 5000 });
  }

  protected confirmDelete(id: string, onClose?: () => void) {
    this.spinner.show();

    return this.entityService.delete(id).pipe(
      tap(() => this.deleteSuccess()),
      finalize(() => {
        this.spinner.hide();
        onClose?.();
      })
    );
  }

  protected deleteSuccess() {
    this.snackbar.open(
      `The ${this.entityName.toLowerCase()} has been successfully deleted.`,
      'Close',
      { duration: 5000 });
  }
}
