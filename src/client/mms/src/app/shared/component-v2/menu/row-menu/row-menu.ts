import { Component, input, output } from '@angular/core';
import { Menu, MenuItem } from "../menu";
import { MatMenuTrigger } from "@angular/material/menu";
import { MatIcon } from "@angular/material/icon";

@Component({
  selector: 'app-row-menu',
  imports: [Menu, MatIcon, MatMenuTrigger],
  templateUrl: './row-menu.html',
  styleUrl: './row-menu.css',
})
export class RowMenu {
  canEdit = input<boolean>(false);
  canDelete = input<boolean>(false);

  view = output<void>();
  edit = output<void>();
  delete = output<void>();

  items: MenuItem[] = [
    { label: 'View', icon: 'visibility', action: 'view' },
    { label: 'Edit', icon: 'edit', action: 'edit' },
    { label: 'Delete', icon: 'delete', action: 'delete' },
  ];

  onAction(action: string): void {
    switch (action) {
      case 'view':
        this.view.emit();
        break;
      case 'edit':
        this.edit.emit();
        break;
      case 'delete':
        this.delete.emit();
        break;
      default:
        break;
    }
  }
}
