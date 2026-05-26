import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { InputField } from "../../../shared/component/form/input/input-field";
import { AuthService } from '../../../service/auth/auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router, RouterLink } from '@angular/router';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { ValidationError } from "../../../shared/component/form/error/validation-error";
import { NgxSpinnerService } from 'ngx-spinner';
import { finalize } from 'rxjs';
import { Button } from "../../../shared/component/button/button";

@Component({
  selector: 'app-login',
  imports: [InputField, ReactiveFormsModule, ValidationError, RouterLink, Button],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  form!: FormGroup;

  constructor(private readonly formBuilder: FormBuilder, private readonly authService: AuthService,
    private readonly router: Router, private readonly spinner: NgxSpinnerService
  ) {
    this.form = this.formBuilder.group({
      username: ['', [CustomValidators.required("user.username.required")]],
      password: ['', [CustomValidators.required("user.password.required")]],
    });
  }

  onSubmit() {
    this.form.markAllAsTouched();

    if (this.form.valid) {
      this.spinner.show();

      this.authService.login(this.form.value)
        .pipe(finalize(() => this.spinner.hide()))
        .subscribe({
          error: (res: HttpErrorResponse) => {
            const error = res.error.messages[Object.keys(res.error.messages)[0]];

            this.form.setErrors({ loginFailed: error });
            this.form.markAllAsTouched();
          }
        });
    }
  }

  navigateToRegister() {
    this.router.navigateByUrl('/register');
  }
}
