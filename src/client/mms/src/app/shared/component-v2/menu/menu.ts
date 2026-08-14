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
}

export type MenuItem = {
  label: string;
  icon?: string;
  action: string;
  children?: MenuItem[];
}
