import { FormBuilder, FormGroup } from "@angular/forms";
import { Directive, inject, OnInit, Type } from "@angular/core";
import { BaseEntityModel } from "../../model/base-entity.model";
import { DialogService } from "../../../service/dialog/dialog.service";
import { finalize, takeUntil } from "rxjs";
import { PopupModal } from "../dialog/popup-modal/popup-modal";
import { DialogPopupDataModel } from "../../model/dialog/dialog-popup-data.model";
import { DialogRef } from "@angular/cdk/dialog";
import { RoleConfigModel } from "../../model/role-config.model";
import { DialogFormDataModel } from "../../model/dialog/dialog-form-data.model";
import { BaseDialog } from "../dialog/base/base-dialog";
import { DialogDataModel } from "../../model/dialog/dialog-data.model";
import { NgxSpinnerService } from "ngx-spinner";
import { EntityService } from "../../../service/entity.service";

@Directive()
export abstract class BaseFeature<T extends BaseEntityModel> implements OnInit {
  abstract entityName: string;

  abstract contentCreateEdit: Type<BaseDialog>;
  abstract contentDetail: Type<BaseDialog>;

  abstract roleConfig: RoleConfigModel;

  entityForm!: FormGroup;

  protected readonly dialogService = inject(DialogService);
  protected readonly formBuilder = inject(FormBuilder);
  protected readonly spinner = inject(NgxSpinnerService);

  protected readonly entityService!: EntityService<T>;

  constructor(entityService: EntityService<T>) {
    this.entityService = entityService;
  }

  ngOnInit(): void {
    this.entityForm = this.formBuilder.nonNullable.group({
      ...this.getUpsertGroup().controls
    });
  }

  protected abstract getUpsertGroup(): FormGroup;

  protected saveNew(respondHandler?: () => void): void {
    this.showSpinner();

    this.entityService.create(this.entityForm.value)
      .pipe(finalize(() => this.hideSpinner()))
      .subscribe(() => {
        respondHandler?.();
      });
  }

  protected saveUpdate(id: string, respondHandler?: () => void): void {
    this.showSpinner();

    this.entityService.update(id, this.entityForm.value)
      .pipe(finalize(() => this.hideSpinner()))
      .subscribe(() => {
        respondHandler?.();
      });
  }

  protected confirmDelete(id: string, respondHandler?: () => void): void {
    this.showSpinner();

    this.entityService.delete(id)
      .pipe(finalize(() => this.hideSpinner()))
      .subscribe(() => {
        respondHandler?.();
      });
  }

  onAdd(): void {
    this.entityForm.reset();
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
      .pipe(takeUntil(ref.closed))
      .subscribe((dialog) => {
        if (dialog === this.contentCreateEdit) {
          const editRef = this.displayEdit(item);

          editRef.closed
            .pipe(takeUntil(ref.closed))
            .subscribe(() => {
              ref.close();
              this.onView(item.id);
            });
        } else if (dialog === PopupModal) {
          const deleteRef = this.displayDelete(item.id);

          deleteRef.closed
            .pipe(takeUntil(ref.closed))
            .subscribe(() => ref.close());
        }
      });
  }

  protected displayAdd(): void {
    const dialogData: DialogFormDataModel<T> = {
      title: `Create ${this.entityName}`,
      form: this.entityForm,
    };

    const dialogRef = this.dialogService.openDialog(this.contentCreateEdit, dialogData);

    dialogRef.componentInstance?.dialogService.saveForm$
      .pipe(takeUntil(dialogRef.closed))
      .subscribe(() => {
        if (this.entityForm.valid) {
          this.saveNew();
          dialogRef.close();
        }
      });
  }

  protected displayEdit(item: T): DialogRef<unknown, BaseDialog> {
    this.patchEntityForm(item);

    const dialogData: DialogFormDataModel<T> = {
      title: `Edit ${this.entityName}`,
      model: item,
      form: this.entityForm,
    };

    const dialogRef = this.dialogService.openDialog(this.contentCreateEdit, dialogData);

    dialogRef.componentInstance?.dialogService.saveForm$
      .pipe(takeUntil(dialogRef.closed))
      .subscribe(() => {
        if (this.entityForm.valid) {
          this.saveUpdate(item.id);
          dialogRef.close();
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

    dialogRef.componentInstance?.dialogService.confirmTask$.subscribe(() => {
      this.confirmDelete(id);
      dialogRef.close();
    });

    return dialogRef;
  }

  abstract patchEntityForm(model: T): void;

  protected showSpinner(): void {
    this.spinner.show();
  }

  protected hideSpinner(timeout: number = 500): void {
    setTimeout(() => this.spinner.hide(), timeout);
  }
}
