import { Component } from '@angular/core';
import { DetailImage } from '../../../shared/component/detail/detail-image/detail-image';
import { DetailText } from '../../../shared/component/detail/detail-text/detail-text';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { Detail } from '../../../shared/component/detail/detail-component/detail';
import { PromotionCreateEdit } from '../create-edit/promotion-create-edit';
import { DetailDialog } from '../../../shared/component/dialog/detail/detail-dialog';
import { PromotionModel } from '../../../model/promotion/promotion.model';

@Component({
  selector: 'app-promotion-detail',
  imports: [DetailImage, DetailText, FormatCellPipe, Detail],
  templateUrl: './promotion-detail.html',
  styleUrl: './promotion-detail.css',
})
export class PromotionDetail extends DetailDialog<PromotionModel> {
  protected override updateDialog = PromotionCreateEdit;
}
