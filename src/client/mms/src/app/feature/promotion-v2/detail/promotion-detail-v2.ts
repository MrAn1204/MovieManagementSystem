import { Component, inject } from '@angular/core';
import { DetailContainer } from "../../../shared/component-v2/dialog/detail-container/detail-container";
import { DetailImage } from "../../../shared/component/detail/detail-image/detail-image";
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { DetailDialogV2 } from '../../../shared/component-v2/dialog/detail-dialog/detail-dialog';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { PromotionDetailModel } from '../../../model/promotion/promotion-detail.model';
import { DetailEntityService } from '../../../service/detail-entity.service';
import { PromotionService } from '../../../service/promotion/promotion.service';

@Component({
  selector: 'app-promotion-detail-v2',
  imports: [DetailContainer, DetailImage, DetailText, FormatCellPipe],
  templateUrl: './promotion-detail-v2.html',
  styleUrl: './promotion-detail-v2.css',
})
export class PromotionDetailV2 extends DetailDialogV2<PromotionDetailModel> {
  protected override entityService: DetailEntityService<PromotionDetailModel> = inject(PromotionService);
}
