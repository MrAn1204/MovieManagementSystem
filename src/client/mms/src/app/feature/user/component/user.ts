import { Component } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Search } from '../../../shared/component/search/search';
import { Table } from '../../../shared/component/table/table';
import { SearchableFeature } from '../../../shared/component/feature/searchable-feature';
import { getRoleConfig } from '../../../shared/config/role-config';
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { UserModel } from '../../../model/user/user.model';
import { UserDetailModel } from '../../../model/user/user-detail.model';
import { UserService } from '../../../service/user/user.service';
import { UserCreateEdit } from '../../user/create-edit/user-create-edit';
import { UserDetail } from '../../user/detail/user-detail';
import { UserFilter } from '../../user/filter/user-filter';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { ConstraintService } from '../../../service/constraint.service';

@Component({
  selector: 'app-user',
  imports: [Search, Table, ReactiveFormsModule],
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

  constructor(userService: UserService, private readonly constraintService: ConstraintService) {
    super(userService);
  }

  protected override getFilterGroup(): FormGroup {
    return this.formBuilder.nonNullable.group({
      roleId: [''],
    });
  }

  protected override getUpsertGroup(): FormGroup {
    const constraints = this.constraintService.get(
      'USERNAME_MIN', 'USERNAME_MAX', 'FULLNAME_MIN', 'FULLNAME_MAX',
      'PHONE_MIN', 'PHONE_MAX', 'ADDRESS_MIN', 'ADDRESS_MAX', 'PASSWORD_MIN'
    );

    return this.formBuilder.nonNullable.group(
      {
        username: ['', [
          CustomValidators.required('user.username.required'),
          CustomValidators.size(constraints['USERNAME_MIN'], constraints['USERNAME_MAX'], 'user.username.size'),
        ]],
        fullname: ['', [
          CustomValidators.required('user.fullname.required'),
          CustomValidators.size(constraints['FULLNAME_MIN'], constraints['FULLNAME_MAX'], 'user.fullname.size'),
        ]],
        password: ['', [
          CustomValidators.required('user.password.required'),
          CustomValidators.passwordValid(constraints['PASSWORD_MIN'], 'user.password.invalid')
        ]],
        confirmPassword: ['', [CustomValidators.required('user.confirmPassword.required')]],
        gender: ['', [CustomValidators.required('user.gender.required')]],
        dateOfBirth: ['', [
          CustomValidators.required('user.dob.required'),
          CustomValidators.pastDate('user.dob.past'),
        ]],
        email: ['', [CustomValidators.email('user.email.invalid')]],
        citizenIdNumber: [null, [CustomValidators.minLength(constraints['CITIZEN_ID_MIN'], 'user.citizenId.size')]],
        phoneNumber: ['', [
          CustomValidators.required('user.phone.required'),
          CustomValidators.size(constraints['PHONE_MIN'], constraints['PHONE_MAX'], 'user.phone.size'),
        ]],
        address: [null, [CustomValidators.size(constraints['ADDRESS_MIN'], constraints['ADDRESS_MAX'], 'user.address.size')]],
        score: [0],
        roleIds: [null, [
          CustomValidators.required('user.roles.required'),
          CustomValidators.arrayContainNoNull('user.roles.invalid'),
        ]],
      },
      { validators: CustomValidators.passwordMatch('user.password.mismatched') }
    );
  }

  override patchEntityForm(model: UserModel): void {
    const detailModel = model as UserDetailModel;

    this.entityForm.patchValue({
      username: model.username,
      fullname: model.fullname,
      password: '',
      confirmPassword: '',
      gender: detailModel.gender,
      dateOfBirth: model.dateOfBirth,
      email: model.email,
      citizenIdNumber: detailModel.citizenIdNumber,
      phoneNumber: model.phoneNumber,
      address: detailModel.address,
      score: detailModel.score ?? 0,
      roleIds: model.roles?.map((role) => role.id) ?? [],
    });
  }
}
