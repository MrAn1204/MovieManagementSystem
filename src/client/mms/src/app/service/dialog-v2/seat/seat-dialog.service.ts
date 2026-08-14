import { inject, Injectable, Type } from '@angular/core';
import { SeatModel } from '../../../model/seat/seat.model';
import { BaseDialogV2 } from '../../../shared/component-v2/dialog/base-dialog/base-dialog';
import { EntityService } from '../../entity.service';
import { SeatService } from '../../seat/seat.service';
import { SeatDetailV2 } from '../../../feature/seat-v2/detail/seat-detail-v2';
import { SeatAddEdit } from '../../../feature/seat-v2/add-edit/seat-add-edit';
import { EntityDialogServiceV2 } from '../entity-dialog.service';

@Injectable({
  providedIn: 'root',
})
export class SeatDialogService extends EntityDialogServiceV2<SeatModel> {
  protected override entityName: string = 'Seat';
  protected override entityService: EntityService<SeatModel> = inject(SeatService);
  protected override detailDialog: Type<BaseDialogV2> = SeatDetailV2;
  protected override formDialog: Type<BaseDialogV2> = SeatAddEdit;

}
