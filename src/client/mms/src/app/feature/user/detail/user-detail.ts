import { Component } from '@angular/core';
import { DetailDialog } from '../../../shared/component/dialog/detail/detail-dialog';
import { Detail } from '../../../shared/component/detail/detail-component/detail';
import { DetailText } from '../../../shared/component/detail/detail-text/detail-text';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { UserCreateEdit } from '../create-edit/user-create-edit';
import { UserDetailModel } from '../../../model/user/user-detail.model';
import { Invoice } from "../../invoice/component/invoice";
import { UserSummaryModel } from '../../../model/user/user-summary.model';

@Component({
  selector: 'app-user-detail',
  imports: [Detail, DetailText, FormatCellPipe, Invoice],
  templateUrl: './user-detail.html',
  styleUrl: './user-detail.css',
})
export class UserDetail extends DetailDialog<UserDetailModel> {
  protected override updateDialog = UserCreateEdit;

  get userSummary(): UserSummaryModel {
    return {
      id: this.model?.id ?? '',
      username: this.model?.username ?? '',
      phoneNumber: this.model?.phoneNumber ?? '',
    }
  }
}
