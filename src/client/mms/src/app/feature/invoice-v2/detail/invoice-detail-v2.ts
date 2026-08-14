import { Component } from '@angular/core';
import { InvoiceModel } from '../../../model/invoice/invoice.model';
import { DetailContainer } from "../../../shared/component-v2/dialog/detail-container/detail-container";
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { TicketModel } from '../../../model/ticket/ticket.model';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { DetailDialogV2 } from '../../../shared/component-v2/dialog/detail-dialog/detail-dialog';
import { TicketTable } from "../../ticket/table/ticket-table";

@Component({
  selector: 'app-invoice-detail-v2',
  imports: [DetailContainer, DetailText, FormatCellPipe, TicketTable],
  templateUrl: './invoice-detail-v2.html',
  styleUrl: './invoice-detail-v2.css',
})
export class InvoiceDetailV2 extends DetailDialogV2<InvoiceModel> {
  ticketColumns: TableColumnModel<TicketModel>[] = [
    { key: 'name', label: 'Name', type: 'string' },
    { key: 'movie', label: 'Movie', type: 'id-name' },
    { key: 'price', label: 'Price', type: 'number' },
  ];
}
