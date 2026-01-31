import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputField } from "../../../shared/component/form/input/input-field";
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, InputField],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  form: FormGroup;

  constructor(private readonly formBuilder: FormBuilder, private readonly router: Router) {
    this.form = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(20)]],
      password: ['', [Validators.required]],
      confirmPassword: ['', [Validators.required]],
      fullname: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      dateOfBirth: ['', [Validators.required]],
      email: ['', [Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(15)]],
    });
  }

  onSubmit() {
    console.log(this.form.value);
    if (this.form.valid) {
      console.log("Form valid");
    } else {
      this.form.markAllAsTouched();
      console.log("Form invalid");
    }
  }

  navigateToLogin() {
    this.router.navigateByUrl('/login');
  }
}
