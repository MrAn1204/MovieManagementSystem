import { Directive, signal, Type } from "@angular/core";
import { BaseEntityModel } from "../../model/base-entity.model";
import { BaseFeature } from "./base-feature";
import { FormOptionModel } from "../../model/form-option.model";
import { FormGroup } from "@angular/forms";
import { TableColumnModel } from "../../model/table-column.model";
import { createEmptyPaginatedResult, PaginatedResult } from "../../model/paginated-result.model";

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

  abstract onSearch(): void;

  onChangePageNumber(page: number): void {
    this.searchForm.controls['pageNumber'].setValue(page);
    this.onSearch();
  }

  onChangePageSize(size: number): void {
    this.searchForm.controls['pageSize'].setValue(size);
    this.onSearch();
  }
}
