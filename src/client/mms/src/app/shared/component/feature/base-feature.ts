import { FormBuilder, FormGroup } from "@angular/forms";
import { TableColumnModel } from "../../model/table-column.model";
import { Directive, inject, OnInit, signal, Type } from "@angular/core";
import { createEmptyPaginatedResult, PaginatedResult } from "../../model/paginated-result.model";
import { FormOptionModel } from "../../model/form-option.model";
import { BaseEntityModel } from "../../model/base-entity.model";
import { DialogService } from "../../../service/dialog/dialog.service";
import { takeUntil } from "rxjs";
import { PopupModal } from "../dialog/popup-modal/popup-modal";
import { DialogPopupDataModel } from "../../model/dialog/dialog-popup-data.model";
import { DialogRef } from "@angular/cdk/dialog";
import { RoleConfigModel } from "../../model/role-config.model";
import { DialogFormDataModel } from "../../model/dialog/dialog-form-data.model";
import { BaseDialog } from "../dialog/base/base-dialog";

@Directive()
export abstract class BaseFeature<T extends BaseEntityModel> implements OnInit {
  abstract entityName: string;

  abstract contentCreateEdit: Type<BaseDialog>;
  abstract contentDetail: Type<BaseDialog>;
  abstract contentFilter: Type<unknown>;

  abstract columns: TableColumnModel<T>[];
  abstract sortOptions: FormOptionModel[];

  abstract roleConfig: RoleConfigModel;

  data = signal<PaginatedResult<T>>(createEmptyPaginatedResult<T>());

  searchForm!: FormGroup;

  entityForm!: FormGroup;

  protected readonly dialogService = inject(DialogService);
  protected readonly formBuilder = inject(FormBuilder);

  ngOnInit(): void {
    this.searchForm = this.formBuilder.nonNullable.group({
      keyword: [''],
      sortBy: ['id'],
      sortDirection: ['ASC'],
      pageNumber: [1],
      pageSize: [10],
      ...this.getFilterGroup().controls
    });

    this.entityForm = this.formBuilder.nonNullable.group({
      ...this.getUpsertGroup().controls
    });
  }

  protected abstract getFilterGroup(): FormGroup;

  protected abstract getUpsertGroup(): FormGroup;

  abstract onSearch(): void;

  onChangePageNumber(page: number): void {
    this.searchForm.controls['pageNumber'].setValue(page);
    this.onSearch();
  }

  onChangePageSize(size: number): void {
    this.searchForm.controls['pageSize'].setValue(size);
    this.onSearch();
  }

  protected abstract saveNew(): void;

  protected abstract saveUpdate(id: string): void;

  protected abstract confirmDelete(id: string): void;

  onAdd(): void {
    this.entityForm.reset();
    this.displayAdd();
  }

  abstract onEdit(id: string): void;

  abstract onView(id: string): void;

  abstract onDelete(id: string): void;

  protected displayInfo(item: T): void {
    const ref = this.dialogService.openDialog(this.contentDetail, {
      title: `${this.entityName} Details`,
      model: item,
      roleConfig: this.roleConfig,
    });

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
    const data: DialogFormDataModel<T> = {
      title: `Create ${this.entityName}`,
      form: this.entityForm,
    };

    const dialogRef = this.dialogService.openDialog(this.contentCreateEdit, data);

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

    const data: DialogFormDataModel<T> = {
      title: `Edit ${this.entityName}`,
      model: item,
      form: this.entityForm,
    };

    const dialogRef = this.dialogService.openDialog(this.contentCreateEdit, data);

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
    const data: DialogPopupDataModel = {
      type: 'warning',
      message: `Are you sure you want to delete this ${this.entityName.toLowerCase()}? This action cannot be undone.`,
    }

    const dialogRef = this.dialogService.openDialog(PopupModal, data);

    dialogRef.componentInstance?.dialogService.confirmTask$.subscribe(() => {
      this.confirmDelete(id);
      dialogRef.close();
    });

    return dialogRef;
  }

  abstract patchEntityForm(model: T): void;
}
