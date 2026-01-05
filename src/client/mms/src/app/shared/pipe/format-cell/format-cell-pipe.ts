import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'formatCell',
})
export class FormatCellPipe implements PipeTransform {

  transform(value: any, ...args: unknown[]): string {
    if (value === null || value === undefined) return '---';

    if (Array.isArray(value)) {
      return value.length > 0 ? value.join(', ') : '---';
    }

    return value.toString();

  }

}
