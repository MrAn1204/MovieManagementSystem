import { Directive, signal, Type } from "@angular/core";
import { BaseEntityModel } from "../../model/base-entity.model";
import { BaseFeature } from "./base-feature";
import { FormOptionModel } from "../../model/form-option.model";
import { FormGroup } from "@angular/forms";
import { TableColumnModel } from "../../model/table-column.model";
import { createEmptyPaginatedResult, PaginatedResult } from "../../model/paginated-result.model";
import { finalize } from "rxjs";

@Directive()
export abstract class SearchableFeature<T extends BaseEntityModel> extends BaseFeature<T> {
  abstract contentFilter: Type<unknown>;

  abstract sortOptions: FormOptionModel[];

  abstract columns: TableColumnModel<T>[];

  data = signal<PaginatedResult<T>>(createEmptyPaginatedResult<T>());

  searchForm!: FormGroup;

  override ngOnInit(): void {
    super.ngOnInit();

    this.searchForm = this.formBuilder.nonNullable.group({
      keyword: [''],
      sortBy: ['id'],
      sortDirection: ['ASC'],
      pageNumber: [1],
      pageSize: [10],
      ...this.getFilterGroup().controls
    });
  }

  protected abstract getFilterGroup(): FormGroup;

  onSearch(): void {
    this.showSpinner();

    this.entityService.search!(this.searchForm.value)
      .pipe(finalize(() => this.hideSpinner()))
      .subscribe(res => {
        this.data.set(res);
      });
  }

  onChangePageNumber(page: number): void {
    this.searchForm.controls['pageNumber'].setValue(page);
    this.onSearch();
  }

  onChangePageSize(size: number): void {
    this.searchForm.controls['pageSize'].setValue(size);
    this.onSearch();
  }

  protected override saveNew(respondHandler?: () => void): void {
    super.saveNew(() => {
      respondHandler?.();
      this.onSearch();
    });
  }

  protected override saveUpdate(id: string, respondHandler?: () => void): void {
    super.saveUpdate(id, () => {
      respondHandler?.();
      this.onSearch();
    });
  }

  protected override confirmDelete(id: string, respondHandler?: () => void): void {
    super.confirmDelete(id, () => {
      respondHandler?.();
      this.onSearch();
    });
  }
}
