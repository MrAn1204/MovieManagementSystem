import { Component, input, output, viewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatMenu, MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-menu',
  imports: [MatIconModule, MatMenuModule, MatButtonModule],
  templateUrl: './menu.html',
  styleUrl: './menu.css',
})
export class Menu {
  items = input<MenuItem[]>([]);

  action = output<string>();

  menu = viewChild<MatMenu>('menu');

  triggerAction(action: string): void {
    this.action.emit(action);
  }

  isDisabled(item: MenuItem): boolean {
    if (typeof item.isDisabled === 'boolean') {
      return item.isDisabled;
    } else if (typeof item.isDisabled === 'function') {
      return item.isDisabled();
    }
    return false;
  }
}

export type MenuItem = {
  label: string;
  icon?: string;
  action: string;
  children?: MenuItem[];
  isDisabled?: boolean | (() => boolean);
}

export const COMMON_MENU_ITEMS = {
  ADD: { label: 'Add', icon: 'add', action: 'add' },
  VIEW: { label: 'View', icon: 'visibility', action: 'view' },
  EDIT: { label: 'Edit', icon: 'edit', action: 'edit' },
  DELETE: { label: 'Delete', icon: 'delete', action: 'delete' },
};
