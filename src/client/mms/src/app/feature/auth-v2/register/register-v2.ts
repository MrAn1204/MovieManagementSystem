import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { AuthService } from '../../../service/auth/auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ConstraintService } from '../../../service/constraint.service';
import { SpinnerService } from '../../../service/ui/spinner/spinner.service';
import { finalize } from 'rxjs';
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { FormSelect } from "../../../shared/component-v2/form/form-select/form-select";
import { MatButtonModule } from '@angular/material/button';
import { MatError } from '@angular/material/form-field';
@Component({
  selector: 'app-register-v2',
  imports: [ReactiveFormsModule, FormInput, FormSelect, MatButtonModule, MatError],
  templateUrl: './register-v2.html',
  styleUrl: './register-v2.css',
})
export class RegisterV2 {
  form: FormGroup;

  genders: FormOptionModel[] = [
    { label: 'Male', value: 'MALE' },
    { label: 'Female', value: 'FEMALE' },
    { label: 'Other', value: 'OTHER' },
  ];

  constructor(private readonly formBuilder: FormBuilder, private readonly authService: AuthService,
    private readonly router: Router, private readonly constraintService: ConstraintService,
    private readonly spinner: SpinnerService
  ) {
    const constrains = this.constraintService.get('USERNAME_MIN', 'USERNAME_MAX', 'FULLNAME_MIN', 'FULLNAME_MAX',
      'PHONE_MIN', 'PHONE_MAX', 'ADDRESS_MIN', 'ADDRESS_MAX', 'PASSWORD_MIN');

    this.form = this.formBuilder.group({
      username: ['', [CustomValidators.required("user.username.required"), CustomValidators.length(constrains['USERNAME_MIN'], constrains['USERNAME_MAX'], 'user.username.size')]],
      password: ['', [CustomValidators.required("user.password.required"), CustomValidators.passwordValid(constrains['PASSWORD_MIN'], "user.password.invalid")]],
      confirmPassword: ['', [CustomValidators.required("user.confirmPassword.required")]],
      fullname: ['', [CustomValidators.required("user.fullname.required"), CustomValidators.length(constrains['FULLNAME_MIN'], constrains['FULLNAME_MAX'], 'user.fullname.size')]],
      gender: [null, [CustomValidators.required("user.gender.required")]],
      dateOfBirth: ['', [CustomValidators.required("user.dob.required")]],
      email: ['', [CustomValidators.email("user.email.invalid")]],
      phoneNumber: ['', [CustomValidators.required("user.phone.required"), CustomValidators.length(constrains['PHONE_MIN'], constrains['PHONE_MAX'], 'user.phone.size')]],
    }, { validators: CustomValidators.passwordMatch("user.password.mismatched") });
  }

  onSubmit() {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      this.spinner.show();
      this.authService.register(this.form.value)
        .pipe(finalize(() => this.spinner.hide()))
        .subscribe({
          next: () => this.navigateToLogin(),
          error: (res: HttpErrorResponse) => {
            this.setServerErrors(res.error.messages);
            this.form.markAllAsTouched();
          }
        });
    }
  }

  navigateToLogin() { this.router.navigateByUrl('/login'); }

  get usernameControl() { return this.form.get('username') as FormControl; }
  get passwordControl() { return this.form.get('password') as FormControl; }
  get confirmPasswordControl() { return this.form.get('confirmPassword') as FormControl; }
  get fullnameControl() { return this.form.get('fullname') as FormControl; }
  get genderControl() { return this.form.get('gender') as FormControl; }
  get dateOfBirthControl() { return this.form.get('dateOfBirth') as FormControl; }
  get phoneNumberControl() { return this.form.get('phoneNumber') as FormControl; }
  get emailControl() { return this.form.get('email') as FormControl; }

  setServerErrors(errorMessages: Record<string, string>) {
    for (const field in errorMessages) {
      const control = this.form.get(field);
      if (control) control.setErrors({ serverError: errorMessages[field] });
      else this.form.setErrors({ serverError: errorMessages[field] });
    }
  }
}
