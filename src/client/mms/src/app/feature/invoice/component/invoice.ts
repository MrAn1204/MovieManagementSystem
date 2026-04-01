import { Component, input, Type } from '@angular/core';
import { BaseFeature } from '../../../shared/component/feature/base-feature';
import { InvoiceModel } from '../../../model/invoice/invoice.model';
import { BaseDialog } from '../../../shared/component/dialog/base/base-dialog';
import { RoleConfigModel } from '../../../shared/model/role-config.model';
import { InvoiceCreateEdit } from '../create-edit/invoice-create-edit';
import { InvoiceDetail } from '../detail/invoice-detail';
import { getRoleConfig } from '../../../shared/config/role-config';
import { Table } from "../../../shared/component/table/table";
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { InvoiceService } from '../../../service/invoice/invoice.service';

@Component({
  selector: 'app-invoice',
  imports: [Table],
  templateUrl: './invoice.html',
  styleUrl: './invoice.css',
})
export class Invoice extends BaseFeature<InvoiceModel> {
  override entityName: string = "Invoice";
  override contentCreateEdit: Type<BaseDialog> = InvoiceCreateEdit;
  override contentDetail: Type<BaseDialog> = InvoiceDetail;
  override roleConfig: RoleConfigModel = getRoleConfig(this.entityName);

  data = input.required<InvoiceModel[]>();

  columns: TableColumnModel<InvoiceModel>[] = [
    { key: 'name', label: 'Name', type: 'string' },
    { key: 'totalMoney', label: 'Total Money', type: 'number' },
    { key: 'addScore', label: 'Score Added ', type: 'number' },
    { key: 'useScore', label: 'Score Used', type: 'number' },
    { key: 'discount', label: 'Discount', type: 'number' },
  ];

  constructor(invoiceService: InvoiceService) {
    super(invoiceService);
  }
}
