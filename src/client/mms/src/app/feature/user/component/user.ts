import { Component } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Search } from '../../../shared/component/search/search';
import { Table } from '../../../shared/component/table/table';
import { SearchableFeature } from '../../../shared/component/feature/searchable-feature';
import { getRoleConfig } from '../../../shared/config/role-config';
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { UserModel } from '../../../model/user/user.model';
import { UserService } from '../../../service/user/user.service';
import { UserCreateEdit } from '../../user/create-edit/user-create-edit';
import { UserDetail } from '../../user/detail/user-detail';
import { UserFilter } from '../../user/filter/user-filter';
import { Pagination } from "../../../shared/component/pagination/pagination";

@Component({
  selector: 'app-user',
  imports: [Search, Table, ReactiveFormsModule, Pagination],
  templateUrl: './user.html',
  styleUrl: './user.css',
})
export class User extends SearchableFeature<UserModel> {
  override entityName = 'User';

  override contentCreateEdit = UserCreateEdit;
  override contentDetail = UserDetail;
  override contentFilter = UserFilter;

  override columns: TableColumnModel<UserModel>[] = [
    { key: 'username', label: 'Username', type: 'string' },
    { key: 'fullname', label: 'Full Name', type: 'string' },
    { key: 'email', label: 'Email', type: 'string' },
    { key: 'phoneNumber', label: 'Phone Number', type: 'string' },
    { key: 'dateOfBirth', label: 'Date of Birth', type: 'date' },
    { key: 'roles', label: 'Roles', type: 'id-name-array' },
  ];

  override sortOptions = [
    { label: 'Username', value: 'username' },
    { label: 'Full Name', value: 'fullname' },
    { label: 'Email', value: 'email' },
    { label: 'Date of Birth', value: 'dateOfBirth' },
    { label: 'Score', value: 'score' },
  ];

  override roleConfig = getRoleConfig(this.entityName);

  constructor(userService: UserService) {
    super(userService);
  }

  protected override getFilterGroup(): FormGroup {
    return this.formBuilder.nonNullable.group({
      roleId: [''],
    });
  }
}
