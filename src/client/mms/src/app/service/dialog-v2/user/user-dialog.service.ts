import { Injectable, Type } from '@angular/core';
import { UserModel } from '../../../model/user/user.model';
import { BaseDialogV2 } from '../../../shared/component-v2/dialog/base-dialog/base-dialog';
import { UserDetailV2 } from '../../../feature/user-v2/detail/user-detail-v2';
import { UserAddEdit } from '../../../feature/user-v2/add-edit/user-add-edit';
import { EntityDialogServiceV2 } from '../entity-dialog.service';

@Injectable({
  providedIn: 'root',
})
export class UserDialogService extends EntityDialogServiceV2<UserModel> {
  protected override entityName: string = 'User';
  protected override detailDialog: Type<BaseDialogV2> = UserDetailV2;
  protected override formDialog: Type<BaseDialogV2> = UserAddEdit;
}
