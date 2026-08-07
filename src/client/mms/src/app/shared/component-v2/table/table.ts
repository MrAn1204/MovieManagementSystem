import { Component, computed, input, output } from '@angular/core';
import { BaseEntityModel } from '../../model/base-entity.model';
import { MatTableModule } from '@angular/material/table';
import { TableColumnModel } from '../../model/table-column.model';
import { FormatCellPipe } from '../../pipe/format-cell/format-cell-pipe';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { SelectionModel } from '@angular/cdk/collections';
import { RowMenu } from "../menu/row-menu/row-menu";
import { RoleConfigModel } from '../../model/role-config.model';
import { TableMenu } from "../menu/table-menu/table-menu";

@Component({
  selector: 'app-table-v2',
  imports: [MatTableModule, FormatCellPipe, MatCheckboxModule, RowMenu, TableMenu],
  templateUrl: './table.html',
  styleUrl: './table.css',
})
export class TableV2<T extends BaseEntityModel> {
  data = input.required<T[]>();
  columns = input.required<TableColumnModel<T>[]>();
  roleConfig = input<RoleConfigModel>();

  addItem = output<void>();
  viewItem = output<string>();
  editItem = output<string>();
  deleteItem = output<string>();

  columnsToDisplay = computed(() => ['select', ...this.columns().map(col => col.key as string), 'menu']);

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
}
