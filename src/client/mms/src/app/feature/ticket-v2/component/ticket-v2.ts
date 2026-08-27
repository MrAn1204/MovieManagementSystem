import { Component, inject, signal } from '@angular/core';
import { TicketFilterV2 } from "../filter/ticket-filter-v2";
import { TicketModel } from '../../../model/ticket/ticket.model';
import { TicketService } from '../../../service/ticket/ticket.service';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { getRoleConfig } from '../../../shared/config/role-config';
import { TableMenuOutput, TableV2 } from "../../../shared/component-v2/table/table";
import { Paginator } from "../../../shared/component-v2/paginator/paginator";
import { TicketAddEdit } from '../add-edit/ticket-add-edit';
import { TicketDetailV2 } from '../detail/ticket-detail-v2';
import { SearchV2 } from "../../../shared/component-v2/search/search";
import { SearchableFeatureV2 } from '../../../shared/component-v2/feature/searchable-feature';
import { EntityService } from '../../../service/entity.service';
import { TicketDialogService } from '../../../service/dialog-v2/ticket/ticket-dialog.service';
import { EntityDialogServiceV2 } from '../../../service/dialog-v2/entity-dialog.service';
import { MenuItem } from '../../../shared/component-v2/menu/menu';
import { InvoiceDialogService } from '../../../service/dialog-v2/invoice/invoice-dialog.service';

@Component({
  selector: 'app-ticket-v2',
  imports: [ReactiveFormsModule, TableV2, Paginator, SearchV2, TicketFilterV2],
  templateUrl: './ticket-v2.html',
  styleUrl: './ticket-v2.css',
})
export class TicketV2 extends SearchableFeatureV2<TicketModel> {
  override entityName = "Ticket";

  override contentAddEdit = TicketAddEdit;
  override contentDetail = TicketDetailV2;

  override sortOptions = [
    { label: 'Price', value: 'price' },
  ]

  override filterForm: FormGroup = this.formBuilder.nonNullable.group({
    showTime: [''],
    movieId: [''],
    roomId: [''],
    promotionId: [''],
  });

  override columns: TableColumnModel<TicketModel>[] = [
    { key: 'name', label: 'Name', type: 'string' },
    { key: 'movie', label: 'Movie', type: 'id-name' },
    { key: 'username' as keyof TicketModel, label: 'Username', type: 'string', getValue: (item) => item.user?.username },
    { key: 'phoneNumber' as keyof TicketModel, label: 'Phone Number', type: 'string', getValue: (item) => item.user?.phoneNumber },
    { key: 'paid', label: 'Status', type: 'string', getValue: (item) => item.paid ? 'Paid' : 'Unpaid' },
  ];

  override roleConfig = getRoleConfig(this.entityName);

  private readonly invoiceDialog = inject(InvoiceDialogService);

  selectedItems = signal<TicketModel[]>([]);

  override get tableMenuItems(): MenuItem[] {
    return [
      ...super.tableMenuItems,
      { label: 'Create Invoice', icon: 'receipt', action: 'create_invoice', isDisabled: () => !this.validateSelected() },
    ];
  }

  onSelect(item: TicketModel[]): void {
    this.selectedItems.set(item);
  }

  validateSelected(): boolean {
    const items = this.selectedItems();
    if (items.length === 0) {
      return false;
    }

    const firstUserId = items[0].user.id;
    return items.every(selected => selected.user.id === firstUserId && !selected.paid);
  }

  override onMenuAction(event: TableMenuOutput): void {
    if (event.action === 'create_invoice' && this.validateSelected()) {
      this.invoiceDialog.displayAdd({
        tickets: this.selectedItems(),
      }).subscribe((res) => res && this.onSearch());
    } else {
      super.onMenuAction(event);
    }
  }

  protected override entityService: EntityService<TicketModel> = inject(TicketService);
  protected override dialogService: EntityDialogServiceV2<TicketModel> = inject(TicketDialogService);
}
