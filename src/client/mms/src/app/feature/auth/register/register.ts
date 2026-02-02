import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputField } from "../../../shared/component/form/input/input-field";
import { Router } from '@angular/router';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { SelectField } from "../../../shared/component/form/select/select-field";
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { AuthService } from '../../../service/auth/auth.service';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, InputField, SelectField],
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
      console.log("Form valid");
      this.authService.register(this.form.value).subscribe((res) => console.log(res));
    } else {
      this.form.markAllAsTouched();
      console.log("Form invalid");
    }
  }

  navigateToLogin() {
    this.router.navigateByUrl('/login');
  }
}
