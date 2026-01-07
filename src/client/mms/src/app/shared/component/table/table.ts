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
  contentCreateEdit = input.required<Type<unknown>>();
  contentDetail = input.required<Type<unknown>>();

  dialogCallbacks = input<Record<string, () => void>>({});

  constructor(private readonly dialogService: DialogService) { }

  viewDetail(item: any): void {
    this.dialogService.openDialog(Detail, {
      title: `${this.entityName()} Details`,
      contentComponent: this.contentDetail(),
      contentInputs: { model: item, },
      callbacks: {
        updateItem: () => this.updateItem(item),
        ...this.dialogCallbacks,
      },
    })
  }

  addItem(): void {
    this.dialogService.openDialog(CreateEdit, {
      title: `Create ${this.entityName()}`,
      contentComponent: this.contentCreateEdit(),
      contentInputs: { mode: 'create' },
    });
  }

  updateItem(item: any): void {
    this.dialogService.openDialog(CreateEdit, {
      title: `Edit ${this.entityName()}`,
      contentComponent: this.contentCreateEdit(),
      contentInputs: { model: item, mode: 'edit', },
    });
  }

  deleteItem() {
    console.log('Delete item');
  }
}
