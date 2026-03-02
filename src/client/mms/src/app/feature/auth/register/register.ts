import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { InputField } from "../../../shared/component/form/input/input-field";
import { Router } from '@angular/router';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { SelectField } from "../../../shared/component/form/select/select-field";
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { AuthService } from '../../../service/auth/auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ValidationError } from "../../../shared/component/form/error/validation-error";

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, InputField, SelectField, ValidationError],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  form: FormGroup;

  genders: FormOptionModel[] = [
    { label: 'Male', value: 'MALE' },
    { label: 'Female', value: 'FEMALE' },
    { label: 'Other', value: 'OTHER' },
  ]

  errorMessages = {
    username: {
      required: 'Username is required',
      minLength: 'Username must be at least 5 characters',
      maxLength: 'Username cannot exceed 20 characters',
    },
    password: {
      required: 'Password is required',
      passwordInvalid: 'Password must have at least 8 characters, one uppercase letter, one lowercase letter, one digit, one special character, and no whitespace.',
    },
    confirmPassword: {
      required: 'Confirm password is required',
      passwordMismatch: 'Passwords do not match',
    },
    fullname: {
      required: 'Full name is required',
      minLength: 'Full name must be at least 3 characters',
      maxLength: 'Full name cannot exceed 50 characters',
    },
    gender: {
      required: 'Gender is required',
    },
    dateOfBirth: {
      required: 'Date of birth is required',
    },
    phoneNumber: {
      required: 'Phone number is required',
      minLength: 'Phone number must be at least 10 characters',
      maxLength: 'Phone number cannot exceed 15 characters',
    },
    email: {
      email: 'Invalid email format',
    },
  };

  constructor(private readonly formBuilder: FormBuilder, private readonly authService: AuthService, private readonly router: Router) {
    this.form = this.formBuilder.group({
      username: ['', [
        CustomValidators.required(this.errorMessages.username.required),
        CustomValidators.minLength(5, this.errorMessages.username.minLength),
        CustomValidators.maxLength(20, this.errorMessages.username.maxLength)
      ]],
      password: ['', [
        CustomValidators.required(this.errorMessages.password.required),
        CustomValidators.passwordValid
      ]],
      confirmPassword: ['', [
        CustomValidators.required(this.errorMessages.confirmPassword.required)
      ]],
      fullname: ['', [
        CustomValidators.required(this.errorMessages.fullname.required),
        CustomValidators.minLength(3, this.errorMessages.fullname.minLength),
        CustomValidators.maxLength(50, this.errorMessages.fullname.maxLength)
      ]],
      gender: ['', [
        CustomValidators.required(this.errorMessages.gender.required)
      ]],
      dateOfBirth: ['', [
        CustomValidators.required(this.errorMessages.dateOfBirth.required)
      ]],
      email: ['', [
        CustomValidators.email(this.errorMessages.email.email)
      ]],
      phoneNumber: ['', [
        CustomValidators.required(this.errorMessages.phoneNumber.required),
        CustomValidators.minLength(10, this.errorMessages.phoneNumber.minLength),
        CustomValidators.maxLength(15, this.errorMessages.phoneNumber.maxLength)
      ]],
    }, { validators: CustomValidators.passwordMatch });
  }

  onSubmit() {
    if (this.form.valid) {
      this.authService.register(this.form.value).subscribe({
        next: () => this.navigateToLogin(),
        error: (res: HttpErrorResponse) => {
          this.setServerErrors(res.error.messages);
          this.form.markAllAsTouched();
        }
      });
    } else {
      this.form.markAllAsTouched();
    }
  }

  navigateToLogin() {
    this.router.navigateByUrl('/login');
  }

  setServerErrors(errorMessages: Record<string, string>) {
    for (const field in errorMessages) {
      const control = this.form.get(field);

      if (control) {
        control.setErrors({ serverError: errorMessages[field] });
      } else {
        this.form.setErrors({ serverError: errorMessages[field] });
      }
    }
  }
}
