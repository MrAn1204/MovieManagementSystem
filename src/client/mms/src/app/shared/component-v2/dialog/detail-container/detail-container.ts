import { Component, input, output } from '@angular/core';
import { ButtonV2 } from "../../button/button";
import { DialogContainer } from "../dialog-container/dialog-container";
import { AuditModel } from '../../../model/audit.model';
import { FormatCellPipe } from '../../../pipe/format-cell/format-cell-pipe';

@Component({
  selector: 'app-detail-container',
  imports: [ButtonV2, DialogContainer, FormatCellPipe],
  templateUrl: './detail-container.html',
  styleUrl: './detail-container.css',
})
export class DetailContainer {
  title = input<string>();
  canEdit = input<boolean>(true);
  canDelete = input<boolean>(true);
  audit = input<AuditModel>();

  closeDialog = output<void>();
  editItem = output<void>();
  deleteItem = output<void>();
}
