import { Component, inject, input, output, Type } from '@angular/core';
import { InvoiceModel } from '../../../model/invoice/invoice.model';
import { BaseDialogV2 } from '../../../shared/component-v2/dialog/base-dialog/base-dialog';
import { RoleConfigModel } from '../../../shared/model/role-config.model';
import { InvoiceAddEdit } from '../add-edit/invoice-add-edit';
import { InvoiceDetailV2 } from '../detail/invoice-detail-v2';
import { getRoleConfig } from '../../../shared/config/role-config';
import { TableV2 } from "../../../shared/component-v2/table/table";
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { InvoiceService } from '../../../service/invoice/invoice.service';
import { UserSummaryModel } from '../../../model/user/user-summary.model';
import { EntityService } from '../../../service/entity.service';
import { InvoiceDialogService } from '../../../service/dialog-v2/invoice/invoice-dialog.service';
import { EntityDialogServiceV2 } from '../../../service/dialog-v2/entity-dialog.service';
import { TableFeature } from '../../../shared/component-v2/feature/table-feature';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-invoice-v2',
  imports: [TableV2],
  templateUrl: './invoice-v2.html',
  styleUrl: './invoice-v2.css',
})
export class InvoiceV2 extends TableFeature<InvoiceModel> {
  override entityName: string = "Invoice";
  override contentAddEdit: Type<BaseDialogV2> = InvoiceAddEdit;
  override contentDetail: Type<BaseDialogV2> = InvoiceDetailV2;
  override roleConfig: RoleConfigModel = getRoleConfig(this.entityName);

  data = input.required<InvoiceModel[]>();
  user = input.required<UserSummaryModel>();

  viewInvoice = output<boolean>();

  override columns: TableColumnModel<InvoiceModel>[] = [
    { key: 'name', label: 'Name', type: 'string' },
    { key: 'totalMoney', label: 'Total Money', type: 'number' },
    { key: 'addScore', label: 'Score Added', type: 'number' },
    { key: 'useScore', label: 'Score Used', type: 'number' },
    { key: 'discount', label: 'Discount', type: 'number' },
  ];

  protected override entityService: EntityService<InvoiceModel> = inject(InvoiceService);
  protected override dialogService: EntityDialogServiceV2<InvoiceModel> = inject(InvoiceDialogService);

  override onView(id: string): void {
    this.viewInvoice.emit(true);

    this.dialogService.showDetailDialog(id).subscribe(result => this.handleViewResult(result, id));
  }

  protected override handleViewResult(result: string, id: string): void {
    if (!result) {
      this.viewInvoice.emit(false);
    } else {
      super.handleViewResult(result, id);
    }
  }

  override onEdit(id: string): void {
    this.dialogService.showAddEditDialog(id)
      .pipe(finalize(() => this.viewInvoice.emit(false)))
      .subscribe();
  }

  override onDelete(id: string): void {
    this.dialogService.showDeleteDialog(id)
      .pipe(finalize(() => this.viewInvoice.emit(false)))
      .subscribe();
  }
}
