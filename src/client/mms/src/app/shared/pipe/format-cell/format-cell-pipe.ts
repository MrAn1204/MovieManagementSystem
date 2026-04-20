import { Pipe, PipeTransform } from '@angular/core';
import { CellType } from '../../model/table-column.model';

@Pipe({
  name: 'formatCell',
})
export class FormatCellPipe implements PipeTransform {
  private readonly EMPTY_TEXT = '---';

  transform(value: any, type?: CellType): string {
    if (value === null || value === undefined) return this.EMPTY_TEXT;

    switch (type) {
      case 'date':
        return new Date(value).toLocaleDateString();
      case 'time':
        return new Date(value).toLocaleTimeString();
      case 'datetime':
        return new Date(value).toLocaleString();
      case 'id-name':
        return value.name;
      case 'id-name-array':
        return Array.isArray(value) && value.length
          ? value.map((item: any) => item.name).join(', ')
          : this.EMPTY_TEXT;
      case 'array':
        return Array.isArray(value) && value.length
          ? value.join(', ')
          : this.EMPTY_TEXT;
      case 'percentage':
        return typeof value === 'number' ? `${(value * 100).toFixed(2)}%` : this.EMPTY_TEXT;
      default:
        return String(value).length ? value : this.EMPTY_TEXT;
    }
  }

}
