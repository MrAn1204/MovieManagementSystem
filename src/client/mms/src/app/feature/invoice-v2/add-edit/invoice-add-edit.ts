import { Component, OnInit } from '@angular/core';
import { AddEditDialog } from '../../../shared/component-v2/dialog/add-edit-dialog/add-edit-dialog';
import { AddEditContainer } from "../../../shared/component-v2/dialog/add-edit-container/add-edit-container";
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { CustomValidators } from '../../../shared/util/custom-validators';
import { InvoiceDetailModel } from '../../../model/invoice/invoice-detail.model';

@Component({
  selector: 'app-invoice-add-edit',
  imports: [AddEditContainer, FormInput, DetailText],
  templateUrl: './invoice-add-edit.html',
  styleUrl: './invoice-add-edit.css',
})
export class InvoiceAddEdit extends AddEditDialog<InvoiceDetailModel> implements OnInit {
  override form = this.formBuilder.nonNullable.group({
    addScore: [0, [CustomValidators.min(0, 'invoice.addScore.invalid')]],
    useScore: [0, [CustomValidators.min(0, 'invoice.useScore.invalid')]],
    discount: [0, [CustomValidators.min(0, 'invoice.discount.invalid')]],
    ticketIds: [[] as string[], [CustomValidators.required('invoice.tickets.required')]],
    userId: ['', [CustomValidators.required('user.required')]],
  });

  ngOnInit(): void {
    this.form.patchValue({
      ticketIds: this.model!.tickets.map(ticket => ticket.id),
      userId: this.model!.user.id,
    });
    this.patchForm();
  }

  protected override patchForm(): void {
    const model = this.model;
    if (!model) {
      return;
    }

    this.form.patchValue({
      addScore: model.addScore,
      useScore: model.useScore,
      discount: model.discount,
      ticketIds: model.tickets.map(ticket => ticket.id),
      userId: model.user.id,
    });
  }

  get totalMoney(): number {
    const tickets = this.model!.tickets;
    return tickets.reduce((total, ticket) => total + ticket.price, 0);
  }

  get userDisplay(): string {
    return `${this.model!.user.username} - ${this.model!.user.phoneNumber}`;
  }
}
