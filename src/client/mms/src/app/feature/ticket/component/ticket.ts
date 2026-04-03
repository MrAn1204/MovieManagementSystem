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
import { Button } from "../../../shared/component/button/button";
import { DialogDataModel } from '../../../shared/model/dialog/dialog-data.model';
import { InvoiceCreateEdit } from '../../invoice/create-edit/invoice-create-edit';
import { InvoiceModel } from '../../../model/invoice/invoice.model';
import { finalize } from 'rxjs';
import { FormMapper } from '../../../shared/util/form-mapper';
import { InvoiceService } from '../../../service/invoice/invoice.service';

@Component({
  selector: 'app-ticket',
  imports: [Search, Table, ReactiveFormsModule, Pagination, Button],
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

  private selectedItems: TicketModel[] = [];

  constructor(ticketService: TicketService, private readonly invoiceService: InvoiceService) {
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

  createInvoice(): void {
    if (this.selectedItems.length === 0) {
      return;
    }

    const ticket = this.selectedItems[0];

    if (!ticket || !this.selectedItems.every(selected => selected.user.id === ticket.user.id)) {
      return;
    }

    const invoiceDialogData: DialogDataModel<InvoiceModel> = {
      title: 'Create Invoice',
      tickets: this.selectedItems,
      user: ticket.user,
    }

    const dialogRef = this.dialogService.openDialog(InvoiceCreateEdit, invoiceDialogData)

    dialogRef.componentInstance?.dialogService.saveForm$.subscribe((form) => {
      console.log(form.value);

      this.showSpinner();

      this.invoiceService.create(form.value)
        .pipe(finalize(() => this.hideSpinner()))
        .subscribe({
          next: () => dialogRef.close(),
          error: (res) => FormMapper.mapErrorResponse(res, form)
        });
    });
  }

  select(item: unknown): void {
    const selected = item as TicketModel;

    const index = this.selectedItems.indexOf(selected);

    if (index === -1) {
      this.selectedItems.push(selected);
    } else {
      this.selectedItems.splice(index, 1);
    }
  }

  selectAll(): void {
    if (this.selectedItems.length === this.data().itemCount) {
      this.selectedItems = [];
    } else {
      this.selectedItems = [...this.data().items];
    }
  }
}
