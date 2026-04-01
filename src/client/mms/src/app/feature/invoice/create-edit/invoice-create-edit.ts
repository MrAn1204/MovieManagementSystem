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
  override form = this.createForm();

  override createForm() {
    return this.formBuilder.nonNullable.group({
      totalMoney: [0],
      addScore: [0],
      useScore: [0],
      discount: [0],
      ticketIds: [[] as string[]],
    });
  }

  override patchForm(): void {
    const model = this.data.model;
    if (!model) {
      return;
    }

    this.form.patchValue({
      totalMoney: model.totalMoney,
      addScore: model.addScore,
      useScore: model.useScore,
      discount: model.discount,
      ticketIds: model.tickets.map(ticket => ticket.id),
    });
  }
}
