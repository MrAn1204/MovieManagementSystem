import { FormBuilder, FormGroup } from "@angular/forms";
import { Directive, inject, Type } from "@angular/core";
import { BaseEntityModel } from "../../model/base-entity.model";
import { finalize } from "rxjs";
import { PopupModal } from "../dialog/popup-modal/popup-modal";
import { DialogPopupDataModel } from "../../model/dialog/dialog-popup-data.model";
import { DialogRef } from "@angular/cdk/dialog";
import { RoleConfigModel } from "../../model/role-config.model";
import { BaseDialog } from "../dialog/base/base-dialog";
import { DialogDataModel } from "../../model/dialog/dialog-data.model";
import { EntityService } from "../../../service/entity.service";
import { FormMapper } from "../../util/form-mapper";
import { EntityDialogService } from "../../../service/dialog/entity/entity-dialog.service";
import { DetailDialogDataModel } from "../../model/dialog/detail-dialog-data.model";
import { SpinnerService } from "../../../service/ui/spinner/spinner.service";

@Directive()
export abstract class BaseFeature<T extends BaseEntityModel> {
  abstract entityName: string;

  abstract contentCreateEdit: Type<BaseDialog>;
  abstract contentDetail: Type<BaseDialog>;

  abstract roleConfig: RoleConfigModel;

  protected readonly entityDialog = inject(EntityDialogService);
  protected readonly formBuilder = inject(FormBuilder);
  protected readonly spinner = inject(SpinnerService);

  protected readonly entityService!: EntityService<T>;

  protected selectedItems: T[] = [];

  constructor(entityService: EntityService<T>) {
    this.entityService = entityService;
  }

  protected saveNew(form: FormGroup, respondHandler?: () => void): void {
    this.spinner.show();

    const lowercaseName = this.entityName.toLowerCase();
    const successDialogData: DialogPopupDataModel = {
      type: 'success',
      message: `The ${lowercaseName} has been successfully created.`,
    }

    this.entityService.create(form.value)
      .pipe(finalize(() => this.spinner.hide()))
      .subscribe({
        next: () => {
          respondHandler?.();
          this.entityDialog.openPopup(successDialogData);
        },
        error: (res) => FormMapper.mapErrorResponse(res, form)
      });
  }

  protected saveUpdate(id: string, form: FormGroup, respondHandler?: () => void): void {
    this.spinner.show();

    const lowercaseName = this.entityName.toLowerCase();
    const successDialogData: DialogPopupDataModel = {
      type: 'success',
      message: `The ${lowercaseName} has been successfully updated.`,
    }

    this.entityService.update(id, form.value)
      .pipe(finalize(() => this.spinner.hide()))
      .subscribe({
        next: () => {
          respondHandler?.();
          this.entityDialog.openPopup(successDialogData);
        },
        error: (res) => FormMapper.mapErrorResponse(res, form)
      });
  }

  protected confirmDelete(id: string, form?: FormGroup, respondHandler?: () => void): void {
    this.spinner.show();

    const lowercaseName = this.entityName.toLowerCase();
    const successDialogData: DialogPopupDataModel = {
      type: 'success',
      message: `The ${lowercaseName} has been successfully deleted.`,
    }

    this.entityService.delete(id)
      .pipe(finalize(() => this.spinner.hide()))
      .subscribe({
        next: () => {
          respondHandler?.();
          this.entityDialog.openPopup(successDialogData);
        },
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
    this.spinner.show();

    this.entityService.getById(id)
      .pipe(finalize(() => this.spinner.hide()))
      .subscribe(res => {
        this.displayEdit(res);
      });
  }

  onView(param: string | T): void {
    if (typeof param === 'string') {
      this.spinner.show();

      this.entityService.getById(param)
        .pipe(finalize(() => this.spinner.hide()))
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
    const dialogData: DetailDialogDataModel<T> = {
      title: `${this.entityName} Details`,
      model: item,
      roleConfig: this.roleConfig,
      openEdit: () => this.displayEdit(item, undefined, () => {
        ref.close();
        this.onView(item.id);
      }),
      openDelete: () => this.displayDelete(item.id, () => ref.close())
    }

    const ref = this.entityDialog.openDetail(this.contentDetail, dialogData, () => {
      this.onView(item.id);
      ref.close();
    });
  }

  protected displayAdd(data?: Record<string, unknown>): void {
    const dialogData: DialogDataModel<T> = {
      title: `Create ${this.entityName}`,
      ...data
    };

    const confirmDialogData: DialogPopupDataModel = {
      type: 'info',
      message: `Do you want to create new ${this.entityName.toLowerCase()} with provided information?`,
    }

    const dialogRef = this.entityDialog.openForm(this.contentCreateEdit, dialogData, (form) => {
       const confirmDialogRef = this.entityDialog.openPopup(confirmDialogData, () => {
        confirmDialogRef.close();

        this.saveNew(form, () => dialogRef.close());
      });
    });
  }

  protected displayEdit(item: T, data?: Record<string, unknown>, onClose?: () => void): DialogRef<unknown, BaseDialog> {
    const dialogData: DialogDataModel<T> = {
      title: `Edit ${this.entityName}`,
      model: item,
      ...data
    };

    const confirmDialogData: DialogPopupDataModel = {
      type: 'info',
      message: `Do you want to update this ${this.entityName.toLowerCase()} with provided information?`,
    }

    const dialogRef = this.entityDialog.openForm(this.contentCreateEdit, dialogData, (form) => {
      const confirmDialogRef = this.entityDialog.openPopup(confirmDialogData, () => {
        confirmDialogRef.close();

        this.saveUpdate(item.id, form, () => {
          dialogRef.close();
          onClose?.();
        });
      });
    });

    return dialogRef;
  }

  protected displayDelete(id: string, onClose?: () => void): DialogRef<unknown, PopupModal> {
    const lowercaseName = this.entityName.toLowerCase();

    const dialogData: DialogPopupDataModel = {
      type: 'warning',
      message: `Are you sure you want to delete this ${lowercaseName}? This action cannot be undone.`,
    }

    const dialogRef = this.entityDialog.openPopup(dialogData, () => {
      this.confirmDelete(id, undefined, () => {
        dialogRef.close();
        onClose?.();
      });
    });

    return dialogRef;
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
