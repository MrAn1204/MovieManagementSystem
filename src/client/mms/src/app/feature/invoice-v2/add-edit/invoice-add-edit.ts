import { Component, inject, signal } from '@angular/core';
import { AddEditDialog } from '../../../shared/component-v2/dialog/add-edit-dialog/add-edit-dialog';
import { AddEditContainer } from "../../../shared/component-v2/dialog/add-edit-container/add-edit-container";
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { CustomValidators } from '../../../shared/util/custom-validators';
import { InvoiceDetailModel } from '../../../model/invoice/invoice-detail.model';
import { DetailEntityService } from '../../../service/detail-entity.service';
import { InvoiceService } from '../../../service/invoice/invoice.service';
import { DialogFormDataModel } from '../../../shared/model/dialog/dialog-form-data.model';
import { TicketModel } from '../../../model/ticket/ticket.model';
import { UserSummaryModel } from '../../../model/user/user-summary.model';

export interface InvoiceDialogDataModel extends DialogFormDataModel<InvoiceDetailModel> {
  tickets?: TicketModel[];
}

@Component({
  selector: 'app-invoice-add-edit',
  imports: [AddEditContainer, FormInput, DetailText],
  templateUrl: './invoice-add-edit.html',
  styleUrl: './invoice-add-edit.css',
})
export class InvoiceAddEdit extends AddEditDialog<InvoiceDetailModel> {
  protected override entityService: DetailEntityService<InvoiceDetailModel> = inject(InvoiceService);

  private readonly invoiceData = this.data as InvoiceDialogDataModel;

  override form = this.formBuilder.nonNullable.group({
    addScore: [0, [CustomValidators.min(0, 'invoice.addScore.invalid')]],
    useScore: [0, [CustomValidators.min(0, 'invoice.useScore.invalid')]],
    discount: [0, [CustomValidators.min(0, 'invoice.discount.invalid')]],
    ticketIds: [[] as string[], [CustomValidators.required('invoice.tickets.required')]],
    userId: ['', [CustomValidators.required('user.required')]],
  });

  totalMoney = signal<number>(this.invoiceData.tickets?.reduce((sum, ticket) => sum + ticket.price, 0) ?? 0);

  private readonly userInfo = signal<UserSummaryModel | null>(this.invoiceData.tickets?.[0].user ?? null);

  get userDisplay(): string {
    const user = this.userInfo();
    return user ? `${user.username} - ${user.phoneNumber}` : '';
  }

  protected override mapForm(model: InvoiceDetailModel): void {
    this.onReset({
      addScore: model.addScore,
      useScore: model.useScore,
      discount: model.discount,
      ticketIds: model.tickets.map(ticket => ticket.id),
      userId: model.user.id,
    });

    this.totalMoney.set(model.totalMoney);
    this.userInfo.set(model.user);
  }
}
