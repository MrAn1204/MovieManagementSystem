import { Component, inject, signal } from '@angular/core';
import { AddEditDialog } from '../../../shared/component-v2/dialog/add-edit-dialog/add-edit-dialog';
import { UserDetailModel } from '../../../model/user/user-detail.model';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { RoleService } from '../../../service/role/role.service';
import { ConstraintService } from '../../../service/constraint.service';
import { AuthService } from '../../../service/auth/auth.service';
import { AddEditContainer } from "../../../shared/component-v2/dialog/add-edit-container/add-edit-container";
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { FormSelect } from "../../../shared/component-v2/form/form-select/form-select";
import { FormTextArea } from "../../../shared/component-v2/form/form-textarea/form-textarea";
import { DetailEntityService } from '../../../service/detail-entity.service';
import { UserService } from '../../../service/user/user.service';
import { MatError } from '@angular/material/select';

@Component({
  selector: 'app-user-add-edit',
  imports: [AddEditContainer, FormInput, FormSelect, FormTextArea, MatError],
  templateUrl: './user-add-edit.html',
  styleUrl: './user-add-edit.css',
})
export class UserAddEdit extends AddEditDialog<UserDetailModel> {
  protected override entityService: DetailEntityService<UserDetailModel> = inject(UserService);

  private readonly roleService = inject(RoleService);
  private readonly constraintService = inject(ConstraintService);
  private readonly authService = inject(AuthService);

  readonly genders: FormOptionModel[] = [
    { label: 'Male', value: 'MALE' },
    { label: 'Female', value: 'FEMALE' },
    { label: 'Other', value: 'OTHER' },
  ];

  roles = signal<FormOptionModel[]>([]);
  changingPassword = signal(false);

  override form = this.buildForm();

  private buildForm() {
    const constraints = this.constraintService.get(
      'USERNAME_MIN', 'USERNAME_MAX', 'FULLNAME_MIN', 'FULLNAME_MAX',
      'PHONE_MIN', 'PHONE_MAX', 'ADDRESS_MIN', 'ADDRESS_MAX', 'PASSWORD_MIN', 'CITIZEN_ID_MIN'
    );

    return this.formBuilder.nonNullable.group(
      {
        username: [{ value: '', disabled: this.isEditMode }, [
          CustomValidators.required('user.username.required'),
          CustomValidators.length(constraints['USERNAME_MIN'], constraints['USERNAME_MAX'], 'user.username.size'),
        ]],
        fullname: ['', [
          CustomValidators.required('user.fullname.required'),
          CustomValidators.length(constraints['FULLNAME_MIN'], constraints['FULLNAME_MAX'], 'user.fullname.size'),
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
        email: [null, [CustomValidators.email('user.email.invalid')]],
        citizenIdNumber: [null, [
          CustomValidators.minLength(constraints['CITIZEN_ID_MIN'], 'user.citizenId.size')
        ]],
        phoneNumber: [null, [
          CustomValidators.required('user.phone.required'),
          CustomValidators.length(constraints['PHONE_MIN'], constraints['PHONE_MAX'], 'user.phone.size'),
        ]],
        address: [null, [
          CustomValidators.length(constraints['ADDRESS_MIN'], constraints['ADDRESS_MAX'], 'user.address.size')
        ]],
        score: [0],
        roleIds: [[] as string[], [
          CustomValidators.required('user.roles.required'),
          CustomValidators.arrayContainNoNull('user.roles.invalid'),
        ]],
      },
      { validators: CustomValidators.passwordMatch('user.password.mismatched') }
    );
  }


  protected override mapForm(model: UserDetailModel): void {
    this.onReset({
      username: model.username,
      fullname: model.fullname,
      gender: model.gender,
      dateOfBirth: model.dateOfBirth,
      email: model.email,
      citizenIdNumber: model.citizenIdNumber,
      phoneNumber: model.phoneNumber,
      address: model.address,
      score: model.score,
      roleIds: model.roles.map((role) => role.id),
    });
  }

  get isEditMode(): boolean {
    return !!this.data.id;
  }

  get isAdmin(): boolean {
    return this.authService.includeRoles(['ADMIN']);
  }

  togglePasswordChange(status: boolean): void {
    this.changingPassword.set(status);
    this.applyPasswordMode(status);
  }

  override ngOnInit(): void {
    super.ngOnInit();
    this.applyPasswordMode(!this.isEditMode);
    this.loadRoleOptions();
  }

  private loadRoleOptions(): void {
    this.roleService.getAll().subscribe((roles) => {
      this.roles.set(roles.map((role) => ({ label: role.name, value: role.id })));
    });
  }

  private applyPasswordMode(enabled: boolean): void {
    const passwordControl = this.form.get('password');
    const confirmPasswordControl = this.form.get('confirmPassword');

    if (!passwordControl || !confirmPasswordControl) return;

    if (enabled) {
      passwordControl.enable();
      confirmPasswordControl.enable();
    } else {
      passwordControl.setValue('');
      confirmPasswordControl.setValue('');

      passwordControl.disable();
      confirmPasswordControl.disable();
    }

    passwordControl.updateValueAndValidity();
    confirmPasswordControl.updateValueAndValidity();
  }
}
