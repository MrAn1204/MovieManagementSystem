import { Component, inject, OnInit } from '@angular/core';
import { AddEditContainer } from "../../../shared/component-v2/dialog/add-edit-container/add-edit-container";
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { FormSelect } from "../../../shared/component-v2/form/form-select/form-select";
import { FormTextArea } from "../../../shared/component-v2/form/form-textarea/form-textarea";
import { FormBuilder } from '@angular/forms';
import { ConstraintService } from '../../../service/constraint.service';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { UserService } from '../../../service/user/user.service';
import { AuthService } from '../../../service/auth/auth.service';
import { BaseDialogV2 } from '../../../shared/component-v2/dialog/base-dialog/base-dialog';
import { finalize } from 'rxjs';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { FormMapper } from '../../../shared/util/form-mapper';

@Component({
  selector: 'app-profile-edit',
  imports: [AddEditContainer, FormInput, FormSelect, FormTextArea],
  templateUrl: './profile-edit.html',
  styleUrl: './profile-edit.css',
})
export class ProfileEdit extends BaseDialogV2 implements OnInit {
  private readonly formBuilder = inject(FormBuilder);
  private readonly constraintService = inject(ConstraintService);
  private readonly userService = inject(UserService);
  private readonly authService = inject(AuthService);

  readonly genders: FormOptionModel[] = [
    { label: 'Male', value: 'MALE' },
    { label: 'Female', value: 'FEMALE' },
    { label: 'Other', value: 'OTHER' },
  ];

  form = this.buildForm();

  ngOnInit(): void {
    this.userService.getById(this.authService.getId()).subscribe(user => {
      this.form.reset({
        fullname: user.fullname,
        gender: user.gender,
        dateOfBirth: user.dateOfBirth,
        email: user.email,
        citizenIdNumber: user.citizenIdNumber,
        phoneNumber: user.phoneNumber,
        address: user.address,
        score: user.score,
        roleIds: user.roles.map(role => role.id)
      });
    });
  }

  private buildForm() {
    const constraints = this.constraintService.get(
      'FULLNAME_MIN', 'FULLNAME_MAX', 'PHONE_MIN', 'PHONE_MAX',
      'ADDRESS_MIN', 'ADDRESS_MAX', 'PASSWORD_MIN', 'CITIZEN_ID_MIN'
    );

    return this.formBuilder.nonNullable.group({
      fullname: ['', [
        CustomValidators.required('user.fullname.required'),
        CustomValidators.length(constraints['FULLNAME_MIN'], constraints['FULLNAME_MAX'], 'user.fullname.size'),
      ]],
      gender: ['', [CustomValidators.required('user.gender.required')]],
      dateOfBirth: ['', [
        CustomValidators.required('user.dob.required'),
        CustomValidators.pastDate('user.dob.past'),
      ]],
      email: ['', [CustomValidators.email('user.email.invalid')]],
      citizenIdNumber: ['', [
        CustomValidators.minLength(constraints['CITIZEN_ID_MIN'], 'user.citizenId.size')
      ]],
      phoneNumber: ['', [
        CustomValidators.required('user.phone.required'),
        CustomValidators.length(constraints['PHONE_MIN'], constraints['PHONE_MAX'], 'user.phone.size'),
      ]],
      address: ['', [
        CustomValidators.length(constraints['ADDRESS_MIN'], constraints['ADDRESS_MAX'], 'user.address.size')
      ]],
      score: [{ value: 0, disabled: true }],
      roleIds: [{ value: [] as string[], disabled: true }],
    });
  }

  protected onSubmit() {
    this.form.markAllAsTouched();

    if (!this.form.valid) {
      return;
    }

    this.spinner.show();
    this.userService.update(this.authService.getId(), this.form.getRawValue())
      .pipe(finalize(() => this.spinner.hide()))
      .subscribe({
        next: (res) => this.onClose(res),
        error: (res) => FormMapper.mapErrorResponse(res, this.form)
      });
  }

  protected onReset() {
    this.form.reset();
  }
}
