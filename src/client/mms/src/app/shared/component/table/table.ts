import { Component, inject } from '@angular/core';
import { CreateEdit } from '../dialog/create-edit/create-edit';
import { Dialog } from '@angular/cdk/dialog';

@Component({
  selector: 'app-table',
  imports: [],
  templateUrl: './table.html',
  styleUrl: './table.css',
})
export class Table {
  columns: string[] = [];
  data: any[] = [];

  dialog = inject(Dialog);

  constructor() {
    this.columns = ['Column 1', 'Column 2', 'Column 3'];
    for (let i = 0; i < 10; i++) {
      this.data.push({ field1: 'Data 1', field2: 'Data 2', field3: 'Data 3' })
    }
  }

  viewDetail() {
    console.log('Open detail view');
  }

  addItem() {
    this.dialog.open(CreateEdit, {
      backdropClass: 'bg-space-black/50',
      data: {
        mode: 'create',
        title: 'Create Item',
      }
    });
  }

  updateItem() {
    this.dialog.open(CreateEdit, {
      backdropClass: 'bg-space-black/50',
      data: {
        mode: 'edit',
        title: 'Edit Item',
      }
    });
  }

  deleteItem() {
    console.log('Delete item');
  }
}
