import { Component } from '@angular/core';
import { CreateEditDialog } from '../../../shared/component/dialog/create-edit/create-edit-dialog';
import { InvoiceModel } from '../../../model/invoice/invoice.model';
import { CreateEdit } from "../../../shared/component/create-edit/create-edit";
import { InputField } from "../../../shared/component/form/input/input-field";
import { ValidationError } from "../../../shared/component/form/error/validation-error";
import { ReactiveFormsModule } from '@angular/forms';
import { TicketModel } from '../../../model/ticket/ticket.model';
import { DialogDataModel } from '../../../shared/model/dialog/dialog-data.model';
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { UserSummaryModel } from '../../../model/user/user-summary.model';
import { CustomValidators } from '../../../shared/util/custom-validators';

@Component({
  selector: 'app-invoice-create-edit',
  imports: [CreateEdit, InputField, ValidationError, ReactiveFormsModule, DetailText],
  templateUrl: './invoice-create-edit.html',
  styleUrl: './invoice-create-edit.css',
})
export class InvoiceCreateEdit extends CreateEditDialog<InvoiceModel> {
  private readonly invoiceData = this.data as InvoiceDialogDataModel;
  override form = this.createForm();

  override createForm() {
    return this.formBuilder.nonNullable.group({
      addScore: [0, [CustomValidators.min(0, 'invoice.addScore.invalid')]],
      useScore: [0, [CustomValidators.min(0, 'invoice.useScore.invalid')]],
      discount: [0, [CustomValidators.min(0, 'invoice.discount.invalid')]],
      ticketIds: [this.invoiceData.tickets.map(ticket => ticket.id), [CustomValidators.required('invoice.tickets.required')]],
      userId: [this.invoiceData.user.id, [CustomValidators.required('user.required')]],
    });
  }

  override patchForm(): void {
    const model = this.invoiceData.model;
    if (!model) {
      return;
    }

    this.form.patchValue({
      addScore: model.addScore,
      useScore: model.useScore,
      discount: model.discount,
      ticketIds: model.tickets.map(ticket => ticket.id),
      userId: undefined,
    });
  }

  get totalMoney(): number {
    return this.invoiceData.tickets.reduce((total, ticket) => total + ticket.price, 0);
  }

  get user(): string {
    return `${this.invoiceData.user.username} - ${this.invoiceData.user.phoneNumber}`;
  }
}

interface InvoiceDialogDataModel extends DialogDataModel<InvoiceModel> {
  tickets: TicketModel[],
  user: UserSummaryModel;
}
