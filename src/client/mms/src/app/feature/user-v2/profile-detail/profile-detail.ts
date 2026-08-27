import { Component, inject, OnInit, signal } from '@angular/core';
import { DialogContainer } from "../../../shared/component-v2/dialog/dialog-container/dialog-container";
import { ButtonV2 } from "../../../shared/component-v2/button/button";
import { AuthService } from '../../../service/auth/auth.service';
import { BaseDialogV2 } from '../../../shared/component-v2/dialog/base-dialog/base-dialog';
import { UserDetailModel } from '../../../model/user/user-detail.model';
import { UserService } from '../../../service/user/user.service';
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { MatTabsModule } from '@angular/material/tabs';
import { TableMenuOutput, TableV2 } from "../../../shared/component-v2/table/table";
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { InvoiceModel } from '../../../model/invoice/invoice.model';
import { InvoiceDetailModel } from '../../../model/invoice/invoice-detail.model';
import { COMMON_MENU_ITEMS, MenuItem } from '../../../shared/component-v2/menu/menu';
import { InvoiceDialogService } from '../../../service/dialog-v2/invoice/invoice-dialog.service';

@Component({
  selector: 'app-profile-detail',
  imports: [DialogContainer, ButtonV2, DetailText, FormatCellPipe, MatTabsModule, TableV2],
  templateUrl: './profile-detail.html',
  styleUrl: './profile-detail.css',
})
export class ProfileDetail extends BaseDialogV2 implements OnInit {
  private readonly authService = inject(AuthService);
  private readonly userService = inject(UserService);
  private readonly invoiceDialog = inject(InvoiceDialogService);

  rowMenuItems: MenuItem[] = [
    COMMON_MENU_ITEMS.VIEW
  ];

  invoiceColumns: TableColumnModel<InvoiceModel>[] = [
    { key: 'name', label: 'Name', type: 'string' },
    { key: 'totalMoney', label: 'Total Money', type: 'number' },
    { key: 'addScore', label: 'Score Added', type: 'number' },
    { key: 'useScore', label: 'Score Used', type: 'number' },
    { key: 'discount', label: 'Discount', type: 'number' },
  ];

  user = signal<UserDetailModel | null>(null);

  selectedInvoice = signal<InvoiceDetailModel | null>(null);

  ngOnInit(): void {
    this.userService.getById(this.authService.getId()).subscribe((res) => this.user.set(res));
  }

  openEditProfile(): void {
    this.onClose('edit');
  }

  changePassword(): void {
    this.onClose('password');
  }

  handleInvoiceMenuAction(output: TableMenuOutput): void {
    if (output.action === 'view' && output.item) {
      this.hideSelf();
      this.viewInvoice(output.item.id);
    }
  }

  private viewInvoice(id: string): void {
    this.invoiceDialog.displayInfo(id, {
      hasEdit: false,
      hasDelete: false,
      canEditTicket: false,
      canDeleteTicket: false,
    }).subscribe((res) => res === 'refresh' ? this.viewInvoice(id) : this.onRefresh());
  }
}
