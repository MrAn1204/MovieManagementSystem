import { Component, effect, input, output, Type } from '@angular/core';
import { FormatCellPipe } from '../../pipe/format-cell/format-cell-pipe';
import { TableColumnModel } from '../../model/table-column.model';
import { PaginatedResult } from '../../model/paginated-result.model';
import { BaseEntityModel } from '../../model/base-entity.model';
import { FormGroup } from '@angular/forms';
import { RoleConfigModel } from '../../model/role-config.model';
import { AuthService } from '../../../service/auth/auth.service';

@Component({
  selector: 'app-table',
  imports: [FormatCellPipe],
  templateUrl: './table.html',
  styleUrl: './table.css',
})
export class Table<T extends BaseEntityModel> {
  columns = input.required<TableColumnModel<T>[]>();
  data = input.required<PaginatedResult<T>>();
  entityName = input.required<string>();
  contentCreateEdit = input.required<Type<unknown>>();
  contentDetail = input.required<Type<unknown>>();

  form = input.required<FormGroup>();

  roleConfig = input.required<RoleConfigModel>();

  changePageNumber = output<number>();
  changePageSize = output<number>();

  saveCreate = output<void>();
  saveEdit = output<void>();

  openCreateForm = output<void>();
  openEditForm = output<string>();
  openDetailForm = output<string>();
  openDeleteModal = output<string>();

  pages: (number | null)[] = [];

  constructor(private readonly authService: AuthService) {
    effect(() => {
      this.setPagination();
    });
  }

  setPagination(): void {
    const pageNumber = this.data().pageNumber;
    const pageCount = this.data().pageCount;

    if (pageCount <= 7) {
      this.pages = new Array(pageCount).fill(0).map((_, index) => index + 1);
      return;
    }

    if (pageNumber <= 4) {
      this.pages = new Array(5).fill(0).map((_, index) => index + 1);
      this.pages.push(null, pageCount);
    } else if (pageNumber <= pageCount - 4) {
      this.pages = [1, null, pageNumber - 1, pageNumber, pageNumber + 1, null, pageCount];
    } else {
      this.pages = [1, null, pageCount - 4, pageCount - 3, pageCount - 2, pageCount - 1, pageCount];
    }
  }

  viewDetail(id: string): void {
    this.openDetailForm.emit(id);
  }

  addItem(): void {
    this.openCreateForm.emit();
  }

  updateItem(id: string): void {
    this.openEditForm.emit(id);
  }

  deleteItem(id: string): void {
    this.openDeleteModal.emit(id);
  }

  updatePageSize(event: Event): void {
    this.setPagination();
    const size = Number.parseInt((event.target as HTMLSelectElement).value);
    this.changePageSize.emit(size);
  }

  updatePageNumber(page: number): void {
    if (page < 1 || page > this.data().pageCount) {
      return;
    }
    this.setPagination();
    this.changePageNumber.emit(page);
  }

  canCreate(): boolean {
    return this.authService.includeRoles(this.roleConfig().create);
  }

  canEdit(): boolean {
    return this.authService.includeRoles(this.roleConfig().edit);
  }

  canDelete(): boolean {
    return this.authService.includeRoles(this.roleConfig().delete);
  }

  canView(): boolean {
    return this.authService.includeRoles(this.roleConfig().getById ?? []);
  }
}
