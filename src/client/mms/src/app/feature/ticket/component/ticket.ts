import { Component } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { SearchableFeature } from '../../../shared/component/feature/searchable-feature';
import { Search } from '../../../shared/component/search/search';
import { Table } from '../../../shared/component/table/table';
import { Pagination } from '../../../shared/component/pagination/pagination';
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { getRoleConfig } from '../../../shared/config/role-config';
import { TicketModel } from '../../../model/ticket/ticket.model';
import { TicketService } from '../../../service/ticket/ticket.service';
import { TicketCreateEdit } from '../create-edit/ticket-create-edit';
import { TicketDetail } from '../detail/ticket-detail';
import { TicketFilter } from '../filter/ticket-filter';

@Component({
  selector: 'app-ticket',
  imports: [Search, Table, ReactiveFormsModule, Pagination],
  templateUrl: './ticket.html',
  styleUrl: './ticket.css',
})
export class Ticket extends SearchableFeature<TicketModel> {
  override entityName = 'Ticket';

  override contentCreateEdit = TicketCreateEdit;
  override contentDetail = TicketDetail;
  override contentFilter = TicketFilter;

  override columns: TableColumnModel<TicketModel>[] = [
    { key: 'name', label: 'Name', type: 'string' },
    { key: 'movie', label: 'Movie', type: 'id-name' },
    { key: 'user', label: 'Username', type: 'string', getValue: (item) => item.user?.username },
    { key: 'user', label: 'Phone Number', type: 'string', getValue: (item) => item.user?.phoneNumber },
  ];

  override sortOptions = [
    { label: 'Price', value: 'price' },
  ];

  override roleConfig = getRoleConfig(this.entityName);

  constructor(ticketService: TicketService) {
    super(ticketService);
  }

  protected override getFilterGroup(): FormGroup {
    return this.formBuilder.nonNullable.group({
      showTime: [''],
      movieId: [''],
      roomId: [''],
      promotionId: [''],
    });
  }
}
