import { Component, input, OnInit, Type } from '@angular/core';
import { CreateEdit } from '../dialog/create-edit/create-edit';
import { Detail } from '../dialog/detail/detail';
import { DialogService } from '../../../service/dialog/dialog.service';

@Component({
  selector: 'app-table',
  imports: [],
  templateUrl: './table.html',
  styleUrl: './table.css',
})
export class Table implements OnInit {
  columns = input.required<Map<string, string>>();
  data = input.required<any[]>();
  entityName = input.required<string>();
  layoutCreateEdit = input.required<Type<Component>>();
  layoutDetail = input.required<Type<Component>>();

  columnNames: string[] = [];
  dataGrid: string[][] = [];

  constructor(private readonly dialogService: DialogService) { }

  ngOnInit(): void {
    this.columnNames = Array.from(this.columns().values());
    const columnKeys = Array.from(this.columns().keys());

    for (const item of this.data()) {
      const row = columnKeys.map(key => item[key]);
      this.dataGrid.push(row);
    }
  }

  viewDetail() {
    this.dialogService.openDialog(Detail, {
      title: `${this.entityName()} Details`,
      contentComponent: this.layoutDetail(),
      updateItem: () => this.updateItem(),
    })
  }

  addItem(): void {
    this.dialogService.openDialog(CreateEdit, {
      mode: 'create',
      title: `Create ${this.entityName()}`,
      contentComponent: this.layoutCreateEdit(),
    });
  }

  updateItem() {
    this.dialogService.openDialog(CreateEdit, {
      mode: 'edit',
      title: `Edit ${this.entityName()}`,
      contentComponent: this.layoutCreateEdit(),
    });
  }

  deleteItem() {
    console.log('Delete item');
  }
}
