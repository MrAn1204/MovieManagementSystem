import { Component, input, output } from '@angular/core';
import { ButtonV2 } from "../../button/button";
import { DialogContainer } from "../dialog-container/dialog-container";

@Component({
  selector: 'app-add-edit-container',
  imports: [ButtonV2, DialogContainer],
  templateUrl: './add-edit-container.html',
  styleUrl: './add-edit-container.css',
})
export class AddEditContainer {
  title = input<string>();

  closeDialog = output<void>();
  submitForm = output<void>();
  resetForm = output<void>();
}
