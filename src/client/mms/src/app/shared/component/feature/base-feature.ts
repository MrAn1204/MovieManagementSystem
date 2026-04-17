import { FormBuilder, FormGroup } from "@angular/forms";
import { Directive, inject, Type } from "@angular/core";
import { BaseEntityModel } from "../../model/base-entity.model";
import { DialogService } from "../../../service/dialog/dialog.service";
import { filter, finalize, takeUntil } from "rxjs";
import { PopupModal } from "../dialog/popup-modal/popup-modal";
import { DialogPopupDataModel } from "../../model/dialog/dialog-popup-data.model";
import { DialogRef } from "@angular/cdk/dialog";
import { RoleConfigModel } from "../../model/role-config.model";
import { BaseDialog } from "../dialog/base/base-dialog";
import { DialogDataModel } from "../../model/dialog/dialog-data.model";
import { NgxSpinnerService } from "ngx-spinner";
import { EntityService } from "../../../service/entity.service";
import { FormMapper } from "../../util/form-mapper";

@Directive()
export abstract class BaseFeature<T extends BaseEntityModel> {
  abstract entityName: string;

  abstract contentCreateEdit: Type<BaseDialog>;
  abstract contentDetail: Type<BaseDialog>;

  abstract roleConfig: RoleConfigModel;

  protected readonly dialogService = inject(DialogService);
  protected readonly formBuilder = inject(FormBuilder);
  protected readonly spinner = inject(NgxSpinnerService);

  protected readonly entityService!: EntityService<T>;

  protected selectedItems: T[] = [];

  constructor(entityService: EntityService<T>) {
    this.entityService = entityService;
  }

  protected saveNew(form: FormGroup, respondHandler?: () => void): void {
    this.showSpinner();

    this.entityService.create(form.value)
      .pipe(finalize(() => this.hideSpinner()))
      .subscribe({
        next: () => respondHandler?.(),
        error: (res) => FormMapper.mapErrorResponse(res, form)
      });
  }

  protected saveUpdate(id: string, form: FormGroup, respondHandler?: () => void): void {
    this.showSpinner();

    this.entityService.update(id, form.value)
      .pipe(finalize(() => this.hideSpinner()))
      .subscribe({
        next: () => respondHandler?.(),
        error: (res) => FormMapper.mapErrorResponse(res, form)
      });
  }

  protected confirmDelete(id: string, form?: FormGroup, respondHandler?: () => void): void {
    this.showSpinner();

    this.entityService.delete(id)
      .pipe(finalize(() => this.hideSpinner()))
      .subscribe({
        next: () => respondHandler?.(),
        error: (res) => {
          if (form) {
            FormMapper.mapErrorResponse(res, form);
          }
        }
      });
  }

  onAdd(): void {
    this.displayAdd();
  }

  onEdit(id: string): void {
    this.showSpinner();

    this.entityService.getById(id)
      .pipe(finalize(() => this.hideSpinner()))
      .subscribe(res => {
        this.displayEdit(res);
      });
  }

  onView(param: string | T): void {
    if (typeof param === 'string') {
      this.showSpinner();

      this.entityService.getById(param)
        .pipe(finalize(() => this.hideSpinner()))
        .subscribe(res => {
          this.displayInfo(res);
        });
    } else {
      this.displayInfo(param);
    }
  }

  onDelete(id: string): void {
    this.displayDelete(id);
  }

  protected displayInfo(item: T): void {
    const dialogData: DialogDataModel<T> = {
      title: `${this.entityName} Details`,
      model: item,
      roleConfig: this.roleConfig,
    }

    const ref = this.dialogService.openDialog(this.contentDetail, dialogData);

    ref.componentInstance?.dialogService.openDialog$
      .pipe(
        filter((event) => event.sourceRef === ref),
        takeUntil(ref.closed))
      .subscribe((event) => {
        if (event.dialog === this.contentCreateEdit) {
          const editRef = this.displayEdit(item);

          editRef.closed
            .pipe(takeUntil(ref.closed))
            .subscribe(() => {
              ref.close();
              this.onView(item.id);
            });
        } else if (event.dialog === PopupModal) {
          const deleteRef = this.displayDelete(item.id);

          deleteRef.closed
            .pipe(takeUntil(ref.closed))
            .subscribe(() => ref.close());
        }
      });

    ref.componentInstance?.dialogService.reload$
      .pipe(
        filter((event) => event.sourceRef === ref),
        takeUntil(ref.closed))
      .subscribe(() => {
        this.onView(item.id);
        ref.close();
      });
  }

  protected displayAdd(data?: Record<string, unknown>, dialog: Type<BaseDialog> = this.contentCreateEdit): void {
    const dialogData: DialogDataModel<T> = {
      title: `Create ${this.entityName}`,
      ...data
    };

    const dialogRef = this.dialogService.openDialog(dialog, dialogData);

    dialogRef.componentInstance?.dialogService.saveForm$
      .pipe(takeUntil(dialogRef.closed))
      .subscribe((form) => {
        if (form.valid) {
          this.saveNew(form, () => dialogRef.close());
        }
      });
  }

  protected displayEdit(item: T, data?: Record<string, unknown>, dialog: Type<BaseDialog> = this.contentCreateEdit): DialogRef<unknown, BaseDialog> {
    const dialogData: DialogDataModel<T> = {
      title: `Edit ${this.entityName}`,
      model: item,
      ...data
    };

    const dialogRef = this.dialogService.openDialog(dialog, dialogData);

    dialogRef.componentInstance?.dialogService.saveForm$
      .pipe(takeUntil(dialogRef.closed))
      .subscribe((form) => {
        if (form.valid) {
          this.saveUpdate(item.id, form, () => dialogRef.close());
        }
      });

    return dialogRef;
  }

  protected displayDelete(id: string): DialogRef<unknown, PopupModal> {
    const dialogData: DialogPopupDataModel = {
      type: 'warning',
      message: `Are you sure you want to delete this ${this.entityName.toLowerCase()}? This action cannot be undone.`,
    }

    const dialogRef = this.dialogService.openDialog(PopupModal, dialogData);

    dialogRef.componentInstance?.dialogService.confirmTask$
      .pipe(filter((event) => event.sourceRef === dialogRef))
      .subscribe(() => {
      this.confirmDelete(id, undefined, () => dialogRef.close());
    });

    return dialogRef;
  }

  protected showSpinner(): void {
    this.spinner.show();
  }

  protected hideSpinner(timeout: number = 500): void {
    setTimeout(() => this.spinner.hide(), timeout);
  }

  select(item: T): void {
    const index = this.selectedItems.indexOf(item);

    if (index === -1) {
      this.selectedItems.push(item);
    } else {
      this.selectedItems.splice(index, 1);
    }
  }

  selectAll(items: T[]): void {
    this.selectedItems = [...items];
  }
}
