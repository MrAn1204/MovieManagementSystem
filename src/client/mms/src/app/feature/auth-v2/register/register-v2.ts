import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { AuthService } from '../../../service/auth/auth.service';
import { ConstraintService } from '../../../service/constraint.service';
import { SpinnerService } from '../../../service/ui/spinner/spinner.service';
import { finalize } from 'rxjs';
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { FormSelect } from "../../../shared/component-v2/form/form-select/form-select";
import { MatButtonModule } from '@angular/material/button';
import { MatError } from '@angular/material/form-field';
import { ButtonV2 } from "../../../shared/component-v2/button/button";
import { RegisterRequest } from '../../../model/auth/register-request';
import { ErrorRespondModel } from '../../../shared/model/error-respond.model';
import { FormMapper } from '../../../shared/util/form-mapper';

@Component({
  selector: 'app-register-v2',
  imports: [ReactiveFormsModule, FormInput, FormSelect, MatButtonModule, MatError, ButtonV2],
  templateUrl: './register-v2.html',
  styleUrl: './register-v2.css',
})
export class RegisterV2 {
  form = this.buildForm();

  genders: FormOptionModel[] = [
    { label: 'Male', value: 'MALE' },
    { label: 'Female', value: 'FEMALE' },
    { label: 'Other', value: 'OTHER' },
  ];

  constructor(private readonly authService: AuthService,
    private readonly router: Router,
    private readonly spinner: SpinnerService
  ) { }

  private buildForm() {
    const formBuilder = inject(FormBuilder);
    const constraintService = inject(ConstraintService);

    const constrains = constraintService.get('USERNAME_MIN', 'USERNAME_MAX', 'FULLNAME_MIN', 'FULLNAME_MAX',
      'PHONE_MIN', 'PHONE_MAX', 'ADDRESS_MIN', 'ADDRESS_MAX', 'PASSWORD_MIN');

    return formBuilder.nonNullable.group({
      username: ['', [CustomValidators.required("user.username.required"), CustomValidators.length(constrains['USERNAME_MIN'], constrains['USERNAME_MAX'], 'user.username.size')]],
      password: ['', [CustomValidators.required("user.password.required"), CustomValidators.passwordValid(constrains['PASSWORD_MIN'], "user.password.invalid")]],
      confirmPassword: ['', [CustomValidators.required("user.confirmPassword.required")]],
      fullname: ['', [CustomValidators.required("user.fullname.required"), CustomValidators.length(constrains['FULLNAME_MIN'], constrains['FULLNAME_MAX'], 'user.fullname.size')]],
      gender: ['', [CustomValidators.required("user.gender.required")]],
      dateOfBirth: ['', [CustomValidators.required("user.dob.required")]],
      email: ['', [CustomValidators.email("user.email.invalid")]],
      phoneNumber: ['', [CustomValidators.required("user.phone.required"), CustomValidators.length(constrains['PHONE_MIN'], constrains['PHONE_MAX'], 'user.phone.size')]],
    }, { validators: CustomValidators.passwordMatch("user.password.mismatched") });
  }

  onSubmit() {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      this.spinner.show();
      this.authService.register(this.form.getRawValue() as RegisterRequest)
        .pipe(finalize(() => this.spinner.hide()))
        .subscribe({
          next: () => this.navigateToLogin(),
          error: (res: ErrorRespondModel) => FormMapper.mapErrorResponse(res, this.form)
        });
    }
  }

  navigateToLogin() {
    this.router.navigateByUrl('/v2/login');
  }
}
