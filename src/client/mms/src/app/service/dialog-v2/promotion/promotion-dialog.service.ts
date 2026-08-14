import { inject, Injectable, Type } from '@angular/core';
import { PromotionModel } from '../../../model/promotion/promotion.model';
import { BaseDialogV2 } from '../../../shared/component-v2/dialog/base-dialog/base-dialog';
import { EntityService } from '../../entity.service';
import { PromotionService } from '../../promotion/promotion.service';
import { PromotionDetailV2 } from '../../../feature/promotion-v2/detail/promotion-detail-v2';
import { PromotionAddEdit } from '../../../feature/promotion-v2/add-edit/promotion-add-edit';
import { EntityDialogServiceV2 } from '../entity-dialog.service';

@Injectable({
  providedIn: 'root',
})
export class PromotionDialogService extends EntityDialogServiceV2<PromotionModel> {
  protected override entityName: string = 'Promotion';
  protected override entityService: EntityService<PromotionModel> = inject(PromotionService);
  protected override detailDialog: Type<BaseDialogV2> = PromotionDetailV2;
  protected override formDialog: Type<BaseDialogV2> = PromotionAddEdit;
}
