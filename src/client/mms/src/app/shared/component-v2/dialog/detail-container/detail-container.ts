import { Component, input, output } from '@angular/core';
import { ButtonV2 } from "../../button/button";
import { DialogContainer } from "../dialog-container/dialog-container";

@Component({
  selector: 'app-detail-container',
  imports: [ButtonV2, DialogContainer],
  templateUrl: './detail-container.html',
  styleUrl: './detail-container.css',
})
export class DetailContainer {
  title = input<string>();
  canEdit = input<boolean>(true);
  canDelete = input<boolean>(true)

  closeDialog = output<void>();
  editItem = output<void>();
  deleteItem = output<void>();
}
