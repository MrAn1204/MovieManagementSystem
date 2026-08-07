import { Directive, inject, Type } from "@angular/core";
import { BaseEntityModel } from "../../model/base-entity.model";
import { BaseDialogV2 } from "../dialog/base-dialog/base-dialog";
import { RoleConfigModel } from "../../model/role-config.model";
import { FormBuilder, FormGroup } from "@angular/forms";
import { SpinnerService } from "../../../service/ui/spinner/spinner.service";
import { MatSnackBar } from "@angular/material/snack-bar";
import { EntityService } from "../../../service/entity.service";
import { finalize, Observable, tap } from "rxjs";
import { DialogFormDataModel } from "../../model/dialog/dialog-form-data.model";
import { MatDialogRef } from "@angular/material/dialog";
import { DetailDialogDataModel } from "../../model/dialog/detail-dialog-data.model";
import { NotificationDialogDataModel } from "../../model/dialog/notification-dialog-data.model";
import { DialogServiceV2 } from "../../../service/dialog-v2/dialog.service";

@Directive()
export abstract class BaseFeatureV2<T extends BaseEntityModel> {
  abstract entityName: string;

  abstract contentAddEdit: Type<BaseDialogV2>;
  abstract contentDetail: Type<BaseDialogV2>;

  abstract roleConfig: RoleConfigModel;

  protected readonly dialogService = inject(DialogServiceV2);
  protected readonly formBuilder = inject(FormBuilder);
  protected readonly spinner = inject(SpinnerService);
  protected readonly snackbar = inject(MatSnackBar);

  protected readonly entityService!: EntityService<T>;

  constructor(entityService: EntityService<T>) {
    this.entityService = entityService;
  }

  protected saveNew(form: FormGroup, onClose?: () => void): Observable<T> {
    return this.entityService.create(form.value).pipe(
      tap(() => this.createSuccess()),
      finalize(() => onClose?.())
    );
  }

  protected createSuccess() {
    this.snackbar.open(
      `The ${this.entityName.toLowerCase()} has been successfully created.`,
      'Close',
      { duration: 5000 });
  }

  protected saveUpdate(id: string, form: FormGroup, onClose?: () => void): Observable<T> {
    return this.entityService.update(id, form.value).pipe(
      tap(() => this.updateSuccess()),
      finalize(() => onClose?.())
    );
  }

  protected updateSuccess() {
    this.snackbar.open(
      `The ${this.entityName.toLowerCase()} has been successfully updated.`,
      'Close',
      { duration: 5000 });
  }

  protected confirmDelete(id: string, onClose?: () => void): Observable<null> {
    return this.entityService.delete(id).pipe(
      tap(() => this.deleteSuccess()),
      finalize(() => onClose?.())
    );
  }

  protected deleteSuccess() {
    this.snackbar.open(
      `The ${this.entityName.toLowerCase()} has been successfully deleted.`,
      'Close',
      { duration: 5000 });
  }

  onAdd(): void {
    this.displayAdd();
  }

  onEdit(id: string): void {
    this.spinner.show();

    this.entityService.getById(id)
      .pipe(finalize(() => this.spinner.hide()))
      .subscribe(res => {
        this.displayEdit(res);
      });
  }

  onView(id: string): void {
    this.entityService.getById(id)
      .pipe(finalize(() => this.spinner.hide()))
      .subscribe(res => {
        this.displayInfo(res);
      });
  }

  onDelete(id: string): void {
    this.displayDelete(id);
  }

  protected displayInfo(item: T): void {
    const dialogData: DetailDialogDataModel<T> = {
      title: `${this.entityName} Details`,
      model: item,
      roleConfig: this.roleConfig,
    }

    const dialogRef = this.dialogService.openDetail(this.contentDetail, dialogData);

    dialogRef.afterClosed().subscribe((result) => {
      if (result === 'edit') {
        this.onEdit(item.id);
      } else if (result === 'delete') {
        this.onDelete(item.id);
      }
    });
  }

  protected displayAdd(data?: Record<string, unknown>): void {
    const dialogData: DialogFormDataModel<T> = {
      title: `Create ${this.entityName}`,
      onSubmit: (form) => this.saveNew(form, () => dialogRef.close()),
      ...data
    };

    const dialogRef = this.dialogService.openForm(this.contentAddEdit, dialogData);
  }

  protected displayEdit(item: T, data?: Record<string, unknown>): MatDialogRef<BaseDialogV2, unknown> {
    const dialogData: DialogFormDataModel<T> = {
      title: `Edit ${this.entityName}`,
      model: item,
      onSubmit: (form) => this.saveUpdate(item.id, form, () => dialogRef.close()),
      ...data
    };

    const dialogRef = this.dialogService.openForm(this.contentAddEdit, dialogData);

    return dialogRef;
  }

  protected displayDelete(id: string): MatDialogRef<BaseDialogV2, unknown> {
    const lowercaseName = this.entityName.toLowerCase();

    const dialogData: NotificationDialogDataModel = {
      type: 'warning',
      message: `Are you sure you want to delete this ${lowercaseName}? This action cannot be undone.`,
      onConfirm: () => this.confirmDelete(id, () => dialogRef.close())
    }

    const dialogRef = this.dialogService.openNotification(dialogData);

    return dialogRef;
  }
}
