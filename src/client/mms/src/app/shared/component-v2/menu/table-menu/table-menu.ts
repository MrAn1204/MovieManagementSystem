import { Component, input, output } from '@angular/core';
import { Menu } from "../menu";
import { MatMenuItem } from "@angular/material/menu";
import { MatIcon } from "@angular/material/icon";

@Component({
  selector: 'app-table-menu',
  imports: [Menu, MatMenuItem, MatIcon],
  templateUrl: './table-menu.html',
  styleUrl: './table-menu.css',
})
export class TableMenu {
  canAdd = input<boolean>();

  add = output<void>();
}
