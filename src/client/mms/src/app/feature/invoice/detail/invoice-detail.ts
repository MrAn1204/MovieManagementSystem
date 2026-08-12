import { Component, Type } from '@angular/core';
import { DetailDialog } from '../../../shared/component/dialog/detail/detail-dialog';
import { BaseDialog } from '../../../shared/component/dialog/base/base-dialog';
import { InvoiceCreateEdit } from '../create-edit/invoice-create-edit';
import { Detail } from "../../../shared/component/detail/detail-component/detail";
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { TicketModel } from '../../../model/ticket/ticket.model';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { TicketTable } from "../../ticket/table/ticket-table";
import { InvoiceDetailModel } from '../../../model/invoice/invoice-detail.model';

@Component({
  selector: 'app-invoice-detail',
  imports: [Detail, DetailText, FormatCellPipe, TicketTable],
  templateUrl: './invoice-detail.html',
  styleUrl: './invoice-detail.css',
})
export class InvoiceDetail extends DetailDialog<InvoiceDetailModel> {
  protected override updateDialog: Type<BaseDialog> = InvoiceCreateEdit;

  ticketColumns: TableColumnModel<TicketModel>[] = [
    { key: 'name', label: 'Name', type: 'string' },
    { key: 'movie', label: 'Movie', type: 'id-name' },
    { key: 'price', label: 'Price', type: 'number' },
  ]
}
