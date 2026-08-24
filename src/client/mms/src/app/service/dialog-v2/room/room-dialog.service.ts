import { Injectable, Type } from '@angular/core';
import { RoomModel } from '../../../model/room/room.model';
import { BaseDialogV2 } from '../../../shared/component-v2/dialog/base-dialog/base-dialog';
import { RoomDetailV2 } from '../../../feature/room-v2/detail/room-detail-v2';
import { RoomAddEdit } from '../../../feature/room-v2/add-edit/room-add-edit';
import { EntityDialogServiceV2 } from '../entity-dialog.service';

@Injectable({
  providedIn: 'root',
})
export class RoomDialogService extends EntityDialogServiceV2<RoomModel> {
  protected override entityName: string = 'Room';
  protected override detailDialog: Type<BaseDialogV2> = RoomDetailV2;
  protected override formDialog: Type<BaseDialogV2> = RoomAddEdit;
}
