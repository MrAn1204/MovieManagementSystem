import { Component, inject } from '@angular/core';
import { UserFilterV2 } from "../filter/user-filter-v2";
import { UserModel } from '../../../model/user/user.model';
import { UserService } from '../../../service/user/user.service';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { getRoleConfig } from '../../../shared/config/role-config';
import { TableV2 } from "../../../shared/component-v2/table/table";
import { Paginator } from "../../../shared/component-v2/paginator/paginator";
import { UserAddEdit } from '../add-edit/user-add-edit';
import { UserDetailV2 } from '../detail/user-detail-v2';
import { SearchV2 } from "../../../shared/component-v2/search/search";
import { SearchableFeatureV2 } from '../../../shared/component-v2/feature/searchable-feature';
import { EntityService } from '../../../service/entity.service';
import { UserDialogService } from '../../../service/dialog-v2/user/user-dialog.service';
import { EntityDialogServiceV2 } from '../../../service/dialog-v2/entity-dialog.service';

@Component({
  selector: 'app-user-v2',
  imports: [ReactiveFormsModule, TableV2, Paginator, SearchV2, UserFilterV2],
  templateUrl: './user-v2.html',
  styleUrl: './user-v2.css',
})
export class UserV2 extends SearchableFeatureV2<UserModel> {
  override entityName = "User";

  override contentAddEdit = UserAddEdit;
  override contentDetail = UserDetailV2;

  override sortOptions = [
    { label: 'Username', value: 'username' },
    { label: 'Full Name', value: 'fullname' },
    { label: 'Email', value: 'email' },
    { label: 'Date of Birth', value: 'dateOfBirth' },
    { label: 'Score', value: 'score' },
  ]

  override filterForm: FormGroup = this.formBuilder.nonNullable.group({
    roleId: [''],
  });

  override columns: TableColumnModel<UserModel>[] = [
    { key: 'username', label: 'Username', type: 'string' },
    { key: 'fullname', label: 'Full Name', type: 'string' },
    { key: 'email', label: 'Email', type: 'string' },
    { key: 'phoneNumber', label: 'Phone Number', type: 'string' },
    { key: 'dateOfBirth', label: 'Date of Birth', type: 'date' },
    { key: 'roles', label: 'Roles', type: 'id-name-array' },
  ];

  override roleConfig = getRoleConfig(this.entityName);

  protected override entityService: EntityService<UserModel> = inject(UserService);
  protected override dialogService: EntityDialogServiceV2<UserModel> = inject(UserDialogService);

}
