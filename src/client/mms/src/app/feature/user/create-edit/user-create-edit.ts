import { Component, inject, OnInit, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CreateEditDialog } from '../../../shared/component/dialog/create-edit/create-edit-dialog';
import { CreateEdit } from '../../../shared/component/create-edit/create-edit';
import { InputField } from '../../../shared/component/form/input/input-field';
import { SelectField } from '../../../shared/component/form/select/select-field';
import { MultiselectField } from '../../../shared/component/form/multiselect/multiselect-field';
import { ValidationError } from '../../../shared/component/form/error/validation-error';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { UserDetailModel } from '../../../model/user/user-detail.model';
import { RoleService } from '../../../service/role/role.service';
import { Textarea } from "../../../shared/component/form/textarea/textarea-field";
import { CustomValidators } from '../../../shared/util/custom-validators';
import { ConstraintService } from '../../../service/constraint.service';

@Component({
  selector: 'app-user-create-edit',
  imports: [InputField, SelectField, MultiselectField, ValidationError, ReactiveFormsModule, CreateEdit, Textarea],
  templateUrl: './user-create-edit.html',
  styleUrl: './user-create-edit.css',
})
export class UserCreateEdit extends CreateEditDialog<UserDetailModel> implements OnInit {
  private readonly roleService = inject(RoleService);
  private readonly constraintService = inject(ConstraintService);
  override form = this.createForm();

  readonly genders: FormOptionModel[] = [
    { label: 'Male', value: 'MALE' },
    { label: 'Female', value: 'FEMALE' },
    { label: 'Other', value: 'OTHER' },
  ];

  roles = signal<FormOptionModel[]>([]);
  changingPassword = signal(false);

  override createForm() {
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
        citizenIdNumber: ['', [CustomValidators.minLength(constraints['CITIZEN_ID_MIN'], 'user.citizenId.size')]],
        phoneNumber: ['', [
          CustomValidators.required('user.phone.required'),
          CustomValidators.size(constraints['PHONE_MIN'], constraints['PHONE_MAX'], 'user.phone.size'),
        ]],
        address: ['', [CustomValidators.size(constraints['ADDRESS_MIN'], constraints['ADDRESS_MAX'], 'user.address.size')]],
        score: [0],
        roleIds: [[] as string[], [
          CustomValidators.required('user.roles.required'),
          CustomValidators.arrayContainNoNull('user.roles.invalid'),
        ]],
      },
      { validators: CustomValidators.passwordMatch('user.password.mismatched') }
    );
  }

  get isEditMode(): boolean {
    return !!this.data.model;
  }

  ngOnInit(): void {
    this.patchForm();
    this.applyPasswordMode(!this.isEditMode);
    this.loadRoleOptions();
  }

  override patchForm(): void {
    const model = this.data.model;
    if (!model) {
      return;
    }

    this.form.patchValue({
      username: model.username,
      fullname: model.fullname,
      password: '',
      confirmPassword: '',
      gender: model.gender,
      dateOfBirth: model.dateOfBirth,
      email: model.email,
      citizenIdNumber: model.citizenIdNumber,
      phoneNumber: model.phoneNumber,
      address: model.address,
      score: model.score ?? 0,
      roleIds: model.roles?.map((role) => role.id) ?? [],
    });
  }

  togglePasswordChange(status: boolean): void {
    this.changingPassword.set(status);
    this.applyPasswordMode(status);
  }

  private loadRoleOptions(): void {
    const selectedRoleIds = this.data.model?.roles?.map((role) => role.id) ?? [];

    this.roleService.getAll().subscribe((roles) => {
      this.roles.set(
        roles.map((role) => ({
          label: role.name,
          value: role.id,
          selected: selectedRoleIds.includes(role.id),
        }))
      );
    });
  }

  private applyPasswordMode(enabled: boolean): void {
    const passwordControl = this.form.get('password');
    const confirmPasswordControl = this.form.get('confirmPassword');

    if (!passwordControl || !confirmPasswordControl) {
      return;
    }

    if (enabled) {
      passwordControl.enable();
      confirmPasswordControl.enable();
    } else {
      passwordControl.disable();
      confirmPasswordControl.disable();
    }

    passwordControl.updateValueAndValidity();
    confirmPasswordControl.updateValueAndValidity();
  }
}
