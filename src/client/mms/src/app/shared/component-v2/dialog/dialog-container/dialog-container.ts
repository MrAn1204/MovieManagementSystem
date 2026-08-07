import { Component, input, output } from '@angular/core';
import { MatDialogTitle, MatDialogContent, MatDialogActions } from "@angular/material/dialog";
import { ButtonIcon } from "../../button-icon/button-icon";
import { MatIcon } from "@angular/material/icon";

@Component({
  selector: 'app-dialog-container',
  imports: [MatDialogTitle, ButtonIcon, MatIcon, MatDialogContent, MatDialogActions],
  templateUrl: './dialog-container.html',
  styleUrl: './dialog-container.css',
})
export class DialogContainer {
  title = input<string>();
  actionAlign = input<'start' | 'center' | 'end'>('end');
  showCloseButton = input<boolean>(true);

  closeDialog = output<void>();
}
