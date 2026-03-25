import { Component } from '@angular/core';
import { CreateEditDialog } from '../../../shared/component/dialog/create-edit/create-edit-dialog';
import { InvoiceModel } from '../../../model/invoice/invoice.model';
import { CreateEdit } from "../../../shared/component/create-edit/create-edit";
import { InputField } from "../../../shared/component/form/input/input-field";
import { ValidationError } from "../../../shared/component/form/error/validation-error";
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-invoice-create-edit',
  imports: [CreateEdit, InputField, ValidationError, ReactiveFormsModule],
  templateUrl: './invoice-create-edit.html',
  styleUrl: './invoice-create-edit.css',
})
export class InvoiceCreateEdit extends CreateEditDialog<InvoiceModel> {

}
