import { Component, input, output } from '@angular/core';
import { FormatCellPipe } from '../../pipe/format-cell/format-cell-pipe';
import { TableColumnModel } from '../../model/table-column.model';
import { BaseEntityModel } from '../../model/base-entity.model';
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
  data = input.required<T[]>();

  roleConfig = input.required<RoleConfigModel>();

  saveCreate = output<void>();
  saveEdit = output<void>();

  openCreateForm = output<void>();
  openEditForm = output<string>();
  openDetailForm = output<string>();
  openDeleteModal = output<string>();

  constructor(private readonly authService: AuthService) {

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
