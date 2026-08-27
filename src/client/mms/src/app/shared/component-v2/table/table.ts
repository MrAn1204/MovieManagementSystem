import { Component, computed, input, OnChanges, OnInit, output, SimpleChanges } from '@angular/core';
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
import { MatButtonModule } from "@angular/material/button";

@Component({
  selector: 'app-table-v2',
  imports: [MatTableModule, FormatCellPipe, MatCheckboxModule, Menu, MatMenuModule, MatIcon, MatButtonModule],
  templateUrl: './table.html',
  styleUrl: './table.css',
})
export class TableV2<T extends BaseEntityModel> implements OnInit, OnChanges {
  data = input.required<T[]>();
  columns = input.required<TableColumnModel<T>[]>();
  roleConfig = input<RoleConfigModel>();

  menuAction = output<TableMenuOutput>();

  columnsToDisplay = computed(() => ['select', ...this.columns().map(col => col.key as string), 'menu']);

  tableMenuItems = input<MenuItem[]>([]);

  rowMenuItems = input<MenuItem[]>([]);

  selectItem = output<T[]>();

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

  ngOnInit(): void {
    this.selectedItems.changed.subscribe(() => {
      this.selectItem.emit(this.selectedItems.selected);
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['data']) {
      this.selectedItems.clear();
    }
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
    this.menuAction.emit({ action, item, selected: this.selectedItems.selected });
  }
}

export type TableMenuOutput = {
  action: string;
  item?: BaseEntityModel;
  selected?: BaseEntityModel[];
}
