import { Component } from '@angular/core';

@Component({
  selector: 'app-table',
  imports: [],
  templateUrl: './table.html',
  styleUrl: './table.css',
})
export class Table {
  columns: string[] = [];
  data: any[] = [];

  constructor() {
    this.columns = ['Column 1', 'Column 2', 'Column 3'];
    for (let i = 0; i < 10; i++) {
      this.data.push({ field1: 'Data 1', field2: 'Data 2', field3: 'Data 3' })
    }
  }
}
