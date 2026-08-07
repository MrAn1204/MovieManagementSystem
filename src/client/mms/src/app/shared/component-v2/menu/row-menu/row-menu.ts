import { Component, input, output } from '@angular/core';
import { Menu } from "../menu";
import { MatMenuItem } from "@angular/material/menu";
import { MatIcon } from "@angular/material/icon";

@Component({
  selector: 'app-row-menu',
  imports: [Menu, MatMenuItem, MatIcon],
  templateUrl: './row-menu.html',
  styleUrl: './row-menu.css',
})
export class RowMenu {
  canEdit = input<boolean>(false);
  canDelete = input<boolean>(false);

  view = output<void>();
  edit = output<void>();
  delete = output<void>();
}
