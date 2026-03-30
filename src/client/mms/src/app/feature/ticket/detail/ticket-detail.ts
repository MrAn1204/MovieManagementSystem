import { Component } from '@angular/core';
import { DetailDialog } from '../../../shared/component/dialog/detail/detail-dialog';
import { TicketDetailModel } from '../../../model/ticket/ticket-detail.model';
import { TicketCreateEdit } from '../create-edit/ticket-create-edit';
import { Detail } from '../../../shared/component/detail/detail-component/detail';
import { DetailText } from '../../../shared/component/detail/detail-text/detail-text';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';

@Component({
  selector: 'app-ticket-detail',
  imports: [Detail, DetailText, FormatCellPipe],
  templateUrl: './ticket-detail.html',
  styleUrl: './ticket-detail.css',
})
export class TicketDetail extends DetailDialog<TicketDetailModel> {
  protected override updateDialog = TicketCreateEdit;
}
