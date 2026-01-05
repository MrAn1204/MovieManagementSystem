import { Component, input, Type } from '@angular/core';
import { CreateEdit } from '../dialog/create-edit/create-edit';
import { Detail } from '../dialog/detail/detail';
import { DialogService } from '../../../service/dialog/dialog.service';
import { FormatCellPipe } from '../../pipe/format-cell/format-cell-pipe';

@Component({
  selector: 'app-table',
  imports: [FormatCellPipe],
  templateUrl: './table.html',
  styleUrl: './table.css',
})
export class Table {
  columns = input.required<Map<string, string>>();
  data = input.required<any[]>();
  entityName = input.required<string>();
  layoutCreateEdit = input.required<Type<unknown>>();
  layoutDetail = input.required<Type<unknown>>();

  constructor(private readonly dialogService: DialogService) { }

  viewDetail(item: any): void {
    this.dialogService.openDialog(Detail, {
      inputs: { model: item },
      title: `${this.entityName()} Details`,
      contentComponent: this.layoutDetail(),
      updateItem: () => this.updateItem(item),
    })
  }

  addItem(): void {
    this.dialogService.openDialog(CreateEdit, {
      mode: 'create',
      title: `Create ${this.entityName()}`,
      contentComponent: this.layoutCreateEdit(),
    });
  }

  updateItem(item: any): void {
    this.dialogService.openDialog(CreateEdit, {
      inputs: { model: item },
      mode: 'edit',
      title: `Edit ${this.entityName()}`,
      contentComponent: this.layoutCreateEdit(),
    });
  }

  deleteItem() {
    console.log('Delete item');
  }
}
