import { Component, inject, input, OnInit, Type } from '@angular/core';
import { CreateEdit } from '../dialog/create-edit/create-edit';
import { Dialog } from '@angular/cdk/dialog';

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
  
  columnNames: string[] = [];
  dataGrid: string[][] = [];

  dialog = inject(Dialog);

  constructor() { }

  ngOnInit(): void {
    this.columnNames = Array.from(this.columns().values());
    const columnKeys = Array.from(this.columns().keys());
    
    for (const item of this.data()) {
      const row = columnKeys.map(key => item[key]);
      this.dataGrid.push(row);
    }
  }

  viewDetail() {
    console.log('Open detail view');
  }

  addItem(): void {
    this.dialog.open(CreateEdit, {
      backdropClass: 'bg-space-black/50',
      data: {
        mode: 'create',
        title: `Create ${this.entityName()}`,
        contentComponent: this.layoutCreateEdit(),
      }
    });
  }

  updateItem() {
    this.dialog.open(CreateEdit, {
      backdropClass: 'bg-space-black/50',
      data: {
        mode: 'edit',
        title: `Edit ${this.entityName()}`,
        contentComponent: this.layoutCreateEdit(),
      }
    });
  }

  deleteItem() {
    console.log('Delete item');
  }
}
