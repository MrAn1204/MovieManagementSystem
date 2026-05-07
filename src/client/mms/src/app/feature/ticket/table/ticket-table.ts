import { Component, input, Type } from '@angular/core';
import { Table } from "../../../shared/component/table/table";
import { BaseFeature } from '../../../shared/component/feature/base-feature';
import { TicketModel } from '../../../model/ticket/ticket.model';
import { BaseDialog } from '../../../shared/component/dialog/base/base-dialog';
import { RoleConfigModel } from '../../../shared/model/role-config.model';
import { TicketCreateEdit } from '../create-edit/ticket-create-edit';
import { TicketDetail } from '../detail/ticket-detail';
import { getRoleConfig } from '../../../shared/config/role-config';
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { TicketService } from '../../../service/ticket/ticket.service';

@Component({
  selector: 'app-ticket-table',
  imports: [Table],
  templateUrl: './ticket-table.html',
  styleUrl: './ticket-table.css',
})
export class TicketTable extends BaseFeature<TicketModel> {
  override entityName: string = 'Ticket';
  override contentCreateEdit: Type<BaseDialog> = TicketCreateEdit;
  override contentDetail: Type<BaseDialog> = TicketDetail;
  override roleConfig: RoleConfigModel = getRoleConfig(this.entityName);

  data = input<TicketModel[]>([]);

  columns: TableColumnModel<TicketModel>[] = [
    { key: 'name', label: 'Name', type: 'string' },
    { key: 'movie', label: 'Movie', type: 'id-name' },
    { key: 'user', label: 'Username', type: 'string', getValue: (item) => item.user?.username },
    { key: 'user', label: 'Phone Number', type: 'string', getValue: (item) => item.user?.phoneNumber },
    { key: 'paid', label: 'Status', type: 'string', getValue: (item) => item.paid ? 'Paid' : 'Unpaid' },
  ];

  constructor(ticketService: TicketService) {
    super(ticketService);
  }
}
