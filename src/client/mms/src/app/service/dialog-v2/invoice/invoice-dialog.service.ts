import { Injectable, Type } from '@angular/core';
import { InvoiceModel } from '../../../model/invoice/invoice.model';
import { BaseDialogV2 } from '../../../shared/component-v2/dialog/base-dialog/base-dialog';
import { InvoiceDetailV2 } from '../../../feature/invoice-v2/detail/invoice-detail-v2';
import { InvoiceAddEdit, InvoiceDialogDataModel } from '../../../feature/invoice-v2/add-edit/invoice-add-edit';
import { EntityDialogServiceV2 } from '../entity-dialog.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class InvoiceDialogService extends EntityDialogServiceV2<InvoiceModel> {
  protected override entityName: string = 'Invoice';
  protected override detailDialog: Type<BaseDialogV2> = InvoiceDetailV2;
  protected override formDialog: Type<BaseDialogV2> = InvoiceAddEdit;

  override displayAdd(data?: InvoiceDialogDataModel): Observable<any> {
    return super.displayAdd(data);
  }
}
