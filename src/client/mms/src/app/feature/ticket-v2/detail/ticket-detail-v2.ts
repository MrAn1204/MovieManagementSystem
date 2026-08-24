import { Component, inject } from '@angular/core';
import { DetailContainer } from "../../../shared/component-v2/dialog/detail-container/detail-container";
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { TicketDetailModel } from '../../../model/ticket/ticket-detail.model';
import { DetailDialogV2 } from '../../../shared/component-v2/dialog/detail-dialog/detail-dialog';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { DetailEntityService } from '../../../service/detail-entity.service';
import { TicketService } from '../../../service/ticket/ticket.service';

@Component({
  selector: 'app-ticket-detail-v2',
  imports: [DetailContainer, DetailText, FormatCellPipe],
  templateUrl: './ticket-detail-v2.html',
  styleUrl: './ticket-detail-v2.css',
})
export class TicketDetailV2 extends DetailDialogV2<TicketDetailModel> {
  protected override entityService: DetailEntityService<TicketDetailModel> = inject(TicketService);
}
