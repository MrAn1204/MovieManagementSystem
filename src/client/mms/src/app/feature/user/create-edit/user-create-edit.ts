import { Component, OnInit, signal } from '@angular/core';
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

@Component({
  selector: 'app-user-create-edit',
  imports: [InputField, SelectField, MultiselectField, ValidationError, ReactiveFormsModule, CreateEdit, Textarea],
  templateUrl: './user-create-edit.html',
  styleUrl: './user-create-edit.css',
})
export class UserCreateEdit extends CreateEditDialog<UserDetailModel> implements OnInit {
  readonly genders: FormOptionModel[] = [
    { label: 'Male', value: 'MALE' },
    { label: 'Female', value: 'FEMALE' },
    { label: 'Other', value: 'OTHER' },
  ];

  roles = signal<FormOptionModel[]>([]);
  changingPassword = signal(false);

  constructor(private readonly roleService: RoleService) {
    super();
  }

  get isEditMode(): boolean {
    return !!this.data.model;
  }

  ngOnInit(): void {
    this.applyPasswordMode(!this.isEditMode);
    this.loadRoleOptions();
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
