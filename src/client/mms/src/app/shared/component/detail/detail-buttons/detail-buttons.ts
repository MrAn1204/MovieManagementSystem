import { Component, output } from '@angular/core';

@Component({
  selector: 'app-detail-buttons',
  imports: [],
  templateUrl: './detail-buttons.html',
  styleUrl: './detail-buttons.css',
})
export class DetailButtons {
  updateItem = output<void>();
  deleteItem = output<void>();
}
