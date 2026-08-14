import { Component, computed, input, output } from '@angular/core';
import { BaseEntityModel } from '../../model/base-entity.model';
import { MatTableModule } from '@angular/material/table';
import { TableColumnModel } from '../../model/table-column.model';
import { FormatCellPipe } from '../../pipe/format-cell/format-cell-pipe';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { SelectionModel } from '@angular/cdk/collections';
import { RoleConfigModel } from '../../model/role-config.model';
import { MenuItem, Menu } from '../menu/menu';
import { MatMenuModule } from "@angular/material/menu";
import { MatIcon } from "@angular/material/icon";

@Component({
  selector: 'app-table-v2',
  imports: [MatTableModule, FormatCellPipe, MatCheckboxModule, Menu, MatMenuModule, MatIcon],
  templateUrl: './table.html',
  styleUrl: './table.css',
})
export class TableV2<T extends BaseEntityModel> {
  data = input.required<T[]>();
  columns = input.required<TableColumnModel<T>[]>();
  roleConfig = input<RoleConfigModel>();

  menuAction = output<TableMenuOutput>();

  columnsToDisplay = computed(() => ['select', ...this.columns().map(col => col.key as string), 'menu']);

  tableMenuItems = input<MenuItem[]>([
    { label: 'Add', icon: 'add', action: 'add' },
  ]);

  rowMenuItems = input<MenuItem[]>([
    { label: 'View', icon: 'visibility', action: 'view' },
    { label: 'Edit', icon: 'edit', action: 'edit' },
    { label: 'Delete', icon: 'delete', action: 'delete' },
  ]);

  protected selectedItems: SelectionModel<T> = new SelectionModel<T>(true, []);

  get canAdd(): boolean {
    return !!(this.roleConfig()?.create);
  }

  get canEdit(): boolean {
    return !!(this.roleConfig()?.edit);
  }

  get canDelete(): boolean {
    return !!(this.roleConfig()?.delete);
  }

  getColumnValue(item: T, column: TableColumnModel<T>): any {
    if (column.getValue) {
      return column.getValue(item);
    }
    return item[column.key];
  }

  getColumnKey(col: TableColumnModel<T>): string {
    return col.key as string;
  }

  isAllSelected() {
    const numSelected = this.selectedItems.selected.length;
    const numRows = this.data().length;
    return numSelected === numRows;
  }

  selectAll() {
    if (this.isAllSelected()) {
      this.selectedItems.clear();
      return;
    }

    this.selectedItems.select(...this.data());
  }

  onMenuAction(action: string, item?: T): void {
    this.menuAction.emit({ action, item });
  }
}

export type TableMenuOutput = {
  action: string;
  item?: BaseEntityModel;
}
