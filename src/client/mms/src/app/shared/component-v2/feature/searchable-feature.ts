import { Directive, OnInit, signal } from "@angular/core";
import { BaseEntityModel } from "../../model/base-entity.model";
import { FormOptionModel } from "../../model/form-option.model";
import { createEmptyPaginatedResult, PaginatedResult } from "../../model/paginated-result.model";
import { FormGroup } from "@angular/forms";
import { finalize } from "rxjs";
import { PageEvent } from "@angular/material/paginator";
import { SearchForm } from "../search/search";
import { TableFeature } from "./table-feature";

@Directive()
export abstract class SearchableFeatureV2<T extends BaseEntityModel> extends TableFeature<T> implements OnInit {
  private readonly DEFAULT_SORT_KEY = 'createdAt';

  data = signal<PaginatedResult<T>>(createEmptyPaginatedResult<T>());

  searchForm: FormGroup<SearchForm> = this.formBuilder.nonNullable.group({
    keyword: [''],
    sortBy: [this.DEFAULT_SORT_KEY],
    sortDirection: ['ASC' as 'ASC' | 'DESC'],
    pageNumber: [1],
    pageSize: [10],
  });

  filterForm?: FormGroup;

  sortOptions?: FormOptionModel[];

  ngOnInit(): void {
    this.onSearch();

    this.sortOptions?.unshift({ label: 'Default', value: this.DEFAULT_SORT_KEY });
  }

  onSearch(): void {
    this.spinner.show();

    const formValue = {
      ...this.searchForm.value,
      ...this.filterForm?.value,
    }

    this.entityService.search!(formValue)
      .pipe(finalize(() => this.spinner.hide()))
      .subscribe(res => this.data.set(res));
  }

  onReset(): void {
    this.searchForm.reset();
    this.filterForm?.reset();
  }

  onPageChange(event: PageEvent): void {
    this.searchForm.controls.pageNumber.setValue(event.pageIndex + 1);
    this.searchForm.controls.pageSize.setValue(event.pageSize);

    this.onSearch();
  }

  override onAdd(): void {
    this.dialogService.showAddEditDialog().subscribe(res => {
      if (res) {
        this.onSearch();
      }
    });
  }

  override onEdit(id: string): void {
    this.dialogService.showAddEditDialog(id).subscribe(res => {
      if (res) {
        this.onSearch();
      }
    });
  }

  override onDelete(id: string): void {
    this.dialogService.showDeleteDialog(id).subscribe(res => {
      if (res) {
        this.onSearch();
      }
    });
  }
}
