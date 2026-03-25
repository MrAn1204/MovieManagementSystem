import { Component } from '@angular/core';
import { CreateEditDialog } from '../../../shared/component/dialog/create-edit/create-edit-dialog';
import { InvoiceModel } from '../../../model/invoice/invoice.model';

@Component({
  selector: 'app-invoice-create-edit',
  imports: [],
  templateUrl: './invoice-create-edit.html',
  styleUrl: './invoice-create-edit.css',
})
export class InvoiceCreateEdit extends CreateEditDialog<InvoiceModel> {

}
