import { Component, effect, input, output, Type } from '@angular/core';
import { CreateEdit } from '../dialog/create-edit/create-edit';
import { Detail } from '../dialog/detail/detail';
import { DialogService } from '../../../service/dialog/dialog.service';
import { FormatCellPipe } from '../../pipe/format-cell/format-cell-pipe';
import { TableColumnModel } from '../../model/table-column.model';
import { PaginatedResult } from '../../model/paginated-result.model';

@Component({
  selector: 'app-table',
  imports: [FormatCellPipe],
  templateUrl: './table.html',
  styleUrl: './table.css',
})
export class Table {
  columns = input.required<TableColumnModel[]>();
  data = input.required<PaginatedResult<any>>();
  entityName = input.required<string>();
  contentCreateEdit = input.required<Type<unknown>>();
  contentDetail = input.required<Type<unknown>>();

  dialogCallbacks = input<Record<string, () => void>>({});

  changePageNumber = output<number>();
  changePageSize = output<number>();

  pages: (number | null)[] = [];

  constructor(private readonly dialogService: DialogService) {
    effect(() => {
      this.setPagination();
    });
  }

  setPagination(): void {
    const pageNumber = this.data().pageNumber;
    const pageCount = this.data().pageCount;

    if (pageCount <= 7) {
      this.pages = new Array(pageCount).fill(0).map((_, index) => index + 1);
      return;
    }

    if (pageNumber <= 4) {
      this.pages = new Array(5).fill(0).map((_, index) => index + 1);
      this.pages.push(null, pageCount);
    } else if (pageNumber <= pageCount - 4) {
      this.pages = [1, null, pageNumber - 1, pageNumber, pageNumber + 1, null, pageCount];
    } else {
      this.pages = [1, null, pageCount - 4, pageCount - 3, pageCount - 2, pageCount - 1, pageCount];
    }
  }

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

  updatePageSize(event: Event): void {
    this.setPagination();
    const size = Number.parseInt((event.target as HTMLSelectElement).value);
    this.changePageSize.emit(size);
  }

  updatePageNumber(page: number): void {
    if (page < 1 || page > this.data().pageCount) {
      return;
    }
    this.setPagination();
    this.changePageNumber.emit(page);
  }
}
