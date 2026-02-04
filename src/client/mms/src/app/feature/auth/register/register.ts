import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
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
      minlength: 'Username must be at least 5 characters',
      maxlength: 'Username cannot exceed 20 characters',
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
      minlength: 'Full name must be at least 3 characters',
      maxlength: 'Full name cannot exceed 50 characters',
    },
    gender: {
      required: 'Gender is required',
    },
    dateOfBirth: {
      required: 'Date of birth is required',
    },
    phoneNumber: {
      required: 'Phone number is required',
    },
    email: {
      email: 'Invalid email format',
    },
  };

  constructor(private readonly formBuilder: FormBuilder, private readonly authService: AuthService, private readonly router: Router) {
    this.form = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(20)]],
      password: ['', [Validators.required, CustomValidators.passwordValid]],
      confirmPassword: ['', [Validators.required]],
      fullname: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      gender: ['', [Validators.required]],
      dateOfBirth: ['', [Validators.required]],
      email: ['', [Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(15)]],
    }, { validators: CustomValidators.passwordMatch });
  }

  onSubmit() {
    console.log(this.form.value);
    if (this.form.valid) {
      this.authService.register(this.form.value).subscribe({
        next: (res) => console.log(res),
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
