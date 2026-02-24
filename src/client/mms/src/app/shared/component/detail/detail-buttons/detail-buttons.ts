import { Component, input, output } from '@angular/core';

@Component({
  selector: 'app-detail-buttons',
  imports: [],
  templateUrl: './detail-buttons.html',
  styleUrl: './detail-buttons.css',
})
export class DetailButtons {
  canEdit = input.required<boolean>();
  canDelete = input.required<boolean>();

  updateItem = output<void>();
  deleteItem = output<void>();
}
