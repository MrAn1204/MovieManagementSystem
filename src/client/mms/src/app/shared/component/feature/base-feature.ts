import { FormControl, FormGroup } from "@angular/forms";
import { TableColumnModel } from "../../model/table-column.model";
import { signal } from "@angular/core";
import { createEmptyPaginatedResult, PaginatedResult } from "../../model/paginated-result.model";
import { FormOptionModel } from "../../model/form-option.model";

export abstract class AppFeature<T> {
  abstract contentCreateEdit: unknown;
  abstract contentDetail: unknown;
  abstract contentFilter: unknown;
  
  abstract columns: TableColumnModel[];
  abstract sortOptions: FormOptionModel[];

  data = signal<PaginatedResult<T>>(createEmptyPaginatedResult<T>());

  searchForm: FormGroup = new FormGroup({
    keyword: new FormControl(''),
    sortBy: new FormControl('id'),
    sortDirection: new FormControl('ASC'),
    pageNumber: new FormControl(1),
    pageSize: new FormControl(10),
  });

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