import { Component, inject } from '@angular/core';
import { InvoiceModel } from '../../../model/invoice/invoice.model';
import { DetailContainer } from "../../../shared/component-v2/dialog/detail-container/detail-container";
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { TicketModel } from '../../../model/ticket/ticket.model';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { DetailDialogV2 } from '../../../shared/component-v2/dialog/detail-dialog/detail-dialog';
import { TableMenuOutput, TableV2 } from "../../../shared/component-v2/table/table";
import { DetailEntityService } from '../../../service/detail-entity.service';
import { InvoiceService } from '../../../service/invoice/invoice.service';
import { TicketDialogService } from '../../../service/dialog-v2/ticket/ticket-dialog.service';
import { finalize } from 'rxjs';
import { TicketService } from '../../../service/ticket/ticket.service';

@Component({
  selector: 'app-invoice-detail-v2',
  imports: [DetailContainer, DetailText, FormatCellPipe, TableV2],
  templateUrl: './invoice-detail-v2.html',
  styleUrl: './invoice-detail-v2.css',
})
export class InvoiceDetailV2 extends DetailDialogV2<InvoiceModel> {
  protected override entityService: DetailEntityService<InvoiceModel> = inject(InvoiceService);

  private readonly ticketService = inject(TicketService);
  private readonly ticketDialog = inject(TicketDialogService);

  ticketColumns: TableColumnModel<TicketModel>[] = [
    { key: 'name', label: 'Name', type: 'string' },
    { key: 'movie', label: 'Movie', type: 'id-name' },
    { key: 'price', label: 'Price', type: 'number' },
  ];

  handleTicketAction(event: TableMenuOutput) {
    const item = event.item;

    if (!item) {
      return;
    }

    this.hideSelf();

    const action = event.action;
    let dialog;

    if (action === 'view') {
      dialog = this.ticketDialog.displayInfo(item.id);
    } else if (action === 'edit') {
      dialog = this.ticketDialog.displayEdit(item.id);
    } else if (action === 'delete') {
      dialog = this.ticketDialog.displayDelete(() => this.ticketService.delete(item.id));
    }

    dialog?.pipe(finalize(() => this.onRefresh())).subscribe();
  }
}
