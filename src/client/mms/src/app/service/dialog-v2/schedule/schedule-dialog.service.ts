import { inject, Injectable, Type } from '@angular/core';
import { ScheduleModel } from '../../../model/schedule/schedule.model';
import { BaseDialogV2 } from '../../../shared/component-v2/dialog/base-dialog/base-dialog';
import { EntityService } from '../../entity.service';
import { ScheduleService } from '../../schedule/schedule.service';
import { ScheduleDetailV2 } from '../../../feature/schedule-v2/detail/schedule-detail-v2';
import { ScheduleAddEdit } from '../../../feature/schedule-v2/add-edit/schedule-add-edit';
import { EntityDialogServiceV2 } from '../entity-dialog.service';

@Injectable({
  providedIn: 'root',
})
export class ScheduleDialogService extends EntityDialogServiceV2<ScheduleModel> {
  protected override entityName: string = 'Schedule';
  protected override entityService: EntityService<ScheduleModel> = inject(ScheduleService);
  protected override detailDialog: Type<BaseDialogV2> = ScheduleDetailV2;
  protected override formDialog: Type<BaseDialogV2> = ScheduleAddEdit;
}
