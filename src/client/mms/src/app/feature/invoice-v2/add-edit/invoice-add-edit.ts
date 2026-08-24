import { Component, inject } from '@angular/core';
import { AddEditDialog } from '../../../shared/component-v2/dialog/add-edit-dialog/add-edit-dialog';
import { AddEditContainer } from "../../../shared/component-v2/dialog/add-edit-container/add-edit-container";
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { CustomValidators } from '../../../shared/util/custom-validators';
import { InvoiceDetailModel } from '../../../model/invoice/invoice-detail.model';
import { DetailEntityService } from '../../../service/detail-entity.service';
import { InvoiceService } from '../../../service/invoice/invoice.service';

@Component({
  selector: 'app-invoice-add-edit',
  imports: [AddEditContainer, FormInput, DetailText],
  templateUrl: './invoice-add-edit.html',
  styleUrl: './invoice-add-edit.css',
})
export class InvoiceAddEdit extends AddEditDialog<InvoiceDetailModel> {
  protected override entityService: DetailEntityService<InvoiceDetailModel> = inject(InvoiceService);

  override form = this.formBuilder.nonNullable.group({
    addScore: [this.model?.addScore ?? 0, [CustomValidators.min(0, 'invoice.addScore.invalid')]],
    useScore: [this.model?.useScore ?? 0, [CustomValidators.min(0, 'invoice.useScore.invalid')]],
    discount: [this.model?.discount ?? 0, [CustomValidators.min(0, 'invoice.discount.invalid')]],
    ticketIds: [this.model?.tickets?.map(ticket => ticket.id) ?? [], [CustomValidators.required('invoice.tickets.required')]],
    userId: [this.model?.user?.id ?? '', [CustomValidators.required('user.required')]],
  });

  get totalMoney(): number {
    const tickets = this.model!.tickets;
    return tickets.reduce((total, ticket) => total + ticket.price, 0);
  }

  get userDisplay(): string {
    return `${this.model!.user.username} - ${this.model!.user.phoneNumber}`;
  }
}
