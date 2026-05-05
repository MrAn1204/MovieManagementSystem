import { Component, contentChild, input, OnChanges, output, SimpleChanges, TemplateRef } from '@angular/core';
import { FormatCellPipe } from '../../pipe/format-cell/format-cell-pipe';
import { TableColumnModel } from '../../model/table-column.model';
import { BaseEntityModel } from '../../model/base-entity.model';
import { RoleConfigModel } from '../../model/role-config.model';
import { AuthService } from '../../../service/auth/auth.service';
import { RowActions } from '../menu/row-actions/row-actions';
import { TableActions } from '../menu/table-actions/table-actions';
import { Button } from "../button/button";
import { NgTemplateOutlet } from '@angular/common';

@Component({
  selector: 'app-table',
  imports: [FormatCellPipe, RowActions, TableActions, Button, NgTemplateOutlet],
  templateUrl: './table.html',
  styleUrl: './table.css',
})
export class Table<T extends BaseEntityModel> implements OnChanges {
  columns = input.required<TableColumnModel<T>[]>();
  data = input.required<T[]>();

  roleConfig = input<RoleConfigModel>();

  tableConfig = input<TableConfig>({
    checkbox: true,
    add: true,
    edit: true,
    delete: true,
  });

  rowButtonsTemplate = contentChild<TemplateRef<any>>('rowButtons');
  rowActionsTemplate = contentChild<TemplateRef<any>>('rowActions');

  saveCreate = output<void>();
  saveEdit = output<void>();

  openCreateForm = output<void>();
  openEditForm = output<string>();
  openDetailForm = output<string>();
  openDeleteModal = output<string>();

  selectItem = output<T>();
  selectAllItems = output<T[]>();

  selectedItems = new Map<number, boolean>();

  constructor(private readonly authService: AuthService) {

  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['data']) {
      this.selectedItems.clear();
      this.data().forEach((_, i) => this.selectedItems.set(i, false));
    }
    this.selectAllItems.emit([]);
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
    return this.tableConfig().add !== false && this.authService.includeRoles(this.roleConfig()?.create ?? []);
  }

  canEdit(): boolean {
    return this.tableConfig().edit !== false && this.authService.includeRoles(this.roleConfig()?.edit ?? []);
  }

  canDelete(): boolean {
    return this.tableConfig().delete !== false && this.authService.includeRoles(this.roleConfig()?.delete ?? []);
  }

  canView(): boolean {
    return this.authService.includeRoles(this.roleConfig()?.getById ?? []);
  }

  hasCheckbox(): boolean {
    return this.tableConfig().checkbox !== false;
  }

  select(item: T, index: number): void {
    this.selectedItems.set(index, !this.selectedItems.get(index));
    this.selectItem.emit(item);
  }

  selectAll(): void {
    if (this.isAllSelected()) {
      this.selectedItems.forEach((_, key) => this.selectedItems.set(key, false));
      this.selectAllItems.emit([]);
    } else {
      this.selectedItems.forEach((_, key) => this.selectedItems.set(key, true));
      this.selectAllItems.emit(this.data());
    }
  }

  isAllSelected(): boolean {
    return Array.from(this.selectedItems.values()).every(Boolean);
  }

  getColumnValue(item: T, column: TableColumnModel<T>): any {
    if (column.getValue) {
      return column.getValue(item);
    }
    return item[column.key];
  }
}

interface TableConfig {
  checkbox?: boolean;
  add?: boolean;
  edit?: boolean;
  delete?: boolean;
}
