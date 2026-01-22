import { FormBuilder, FormGroup } from "@angular/forms";
import { TableColumnModel } from "../../model/table-column.model";
import { Directive, inject, OnInit, signal, Type } from "@angular/core";
import { createEmptyPaginatedResult, PaginatedResult } from "../../model/paginated-result.model";
import { FormOptionModel } from "../../model/form-option.model";
import { BaseEntityModel } from "../../model/base-entity.model";
import { DialogService } from "../../../service/dialog/dialog.service";
import { Detail } from "../dialog/detail/detail";
import { takeUntil } from "rxjs";
import { CreateEdit } from "../dialog/create-edit/create-edit";
import { DialogFormDataModel } from "../../model/dialog/dialog-form-data.model";

@Directive()
export abstract class BaseFeature<T extends BaseEntityModel> implements OnInit {
  abstract entityName: string;

  abstract contentCreateEdit: Type<unknown>;
  abstract contentDetail: Type<unknown>;
  abstract contentFilter: Type<unknown>;

  abstract columns: TableColumnModel<T>[];
  abstract sortOptions: FormOptionModel[];

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

  protected abstract saveUpdate(): void;

  onAdd(): void {
    this.entityForm.reset();
    this.displayAdd();
  }

  abstract onEdit(id: string): void;

  abstract onView(id: string): void;

  protected displayInfo(item: any): void {
    const ref = this.dialogService.openDialog(Detail, {
      title: `${this.entityName} Details`,
      contentComponent: this.contentDetail,
      contentInputs: { model: item, },
    });

    ref.componentInstance?.dialogService.openDialog$
      .pipe(takeUntil(ref.closed))
      .subscribe((dialog) => {
        if (dialog === CreateEdit) {
          this.displayEdit(item);
        }
      });
  }

  protected displayAdd(): void {
    const data: DialogFormDataModel = {
      title: `Create ${this.entityName}`,
      contentComponent: this.contentCreateEdit,
      contentInputs: { mode: 'create' },
      form: this.entityForm,
    };

    const dialogRef = this.dialogService.openDialog(CreateEdit, data);

    dialogRef.componentInstance?.dialogService.saveForm$
      .pipe(takeUntil(dialogRef.closed))
      .subscribe(() => this.saveNew());
  }

  protected displayEdit(item: any): void {
    const data: DialogFormDataModel = {
      title: `Edit ${this.entityName}`,
      contentComponent: this.contentCreateEdit,
      contentInputs: { model: item, mode: 'edit', },
      form: this.entityForm,
    };

    const dialogRef = this.dialogService.openDialog(CreateEdit, data);

    dialogRef.componentInstance?.dialogService.saveForm$
      .pipe(takeUntil(dialogRef.closed))
      .subscribe(() => this.saveUpdate());
  }
}