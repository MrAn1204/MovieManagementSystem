import { Directive, OnInit, signal, Type } from "@angular/core";
import { BaseEntityModel } from "../../model/base-entity.model";
import { BaseFeature } from "./base-feature";
import { FormOptionModel } from "../../model/form-option.model";
import { FormGroup } from "@angular/forms";
import { TableColumnModel } from "../../model/table-column.model";
import { createEmptyPaginatedResult, PaginatedResult } from "../../model/paginated-result.model";
import { finalize } from "rxjs";

@Directive()
export abstract class SearchableFeature<T extends BaseEntityModel> extends BaseFeature<T> implements OnInit {
  abstract contentFilter: Type<unknown>;

  abstract sortOptions: FormOptionModel[];

  private readonly DEFAULT_SORT_KEY = 'createdAt';

  abstract columns: TableColumnModel<T>[];

  data = signal<PaginatedResult<T>>(createEmptyPaginatedResult<T>());

  searchForm!: FormGroup;

  ngOnInit(): void {
    this.searchForm = this.formBuilder.nonNullable.group({
      keyword: [''],
      sortBy: [this.DEFAULT_SORT_KEY],
      sortDirection: ['ASC'],
      pageNumber: [1],
      pageSize: [10],
      ...this.getFilterGroup().controls
    });

    this.sortOptions.unshift({ label: 'Default Sort', value: this.DEFAULT_SORT_KEY });

    this.onSearch();
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

  protected override saveNew(form: FormGroup, respondHandler?: () => void): void {
    super.saveNew(form, () => {
      respondHandler?.();
      this.onSearch();
    });
  }

  protected override saveUpdate(id: string, form: FormGroup, respondHandler?: () => void): void {
    super.saveUpdate(id, form, () => {
      respondHandler?.();
      this.onSearch();
    });
  }

  protected override confirmDelete(id: string, form?: FormGroup, respondHandler?: () => void): void {
    super.confirmDelete(id, form, () => {
      respondHandler?.();
      this.onSearch();
    });
  }
}
