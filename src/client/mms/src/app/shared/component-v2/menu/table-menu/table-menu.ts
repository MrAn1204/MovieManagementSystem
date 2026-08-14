import { Component, input, output } from '@angular/core';
import { Menu, MenuItem } from "../menu";
import { MatMenuTrigger } from "@angular/material/menu";
import { MatIcon } from "@angular/material/icon";

@Component({
  selector: 'app-table-menu',
  imports: [Menu, MatIcon, MatMenuTrigger],
  templateUrl: './table-menu.html',
  styleUrl: './table-menu.css',
})
export class TableMenu {
  canAdd = input<boolean>();

  add = output<void>();

  items: MenuItem[] = [
    { label: 'Add', icon: 'add', action: 'add' },
  ];

  onAction(action: string): void {
    if (action === 'add') {
      this.add.emit();
    }
  }
}
