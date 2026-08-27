import { Component, inject } from '@angular/core';
import { DetailContainer } from "../../../shared/component-v2/dialog/detail-container/detail-container";
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { UserDetailModel } from '../../../model/user/user-detail.model';
import { DetailDialogV2 } from '../../../shared/component-v2/dialog/detail-dialog/detail-dialog';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { InvoiceV2 } from '../../invoice-v2/component/invoice-v2';
import { UserService } from '../../../service/user/user.service';
import { DetailEntityService } from '../../../service/detail-entity.service';

@Component({
  selector: 'app-user-detail-v2',
  imports: [DetailContainer, DetailText, FormatCellPipe, InvoiceV2],
  templateUrl: './user-detail-v2.html',
  styleUrl: './user-detail-v2.css',
})
export class UserDetailV2 extends DetailDialogV2<UserDetailModel> {
  protected override entityService: DetailEntityService<UserDetailModel> = inject(UserService);
}
