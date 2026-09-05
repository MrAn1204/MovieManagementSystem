import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { AuthService } from '../../../service/auth/auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router, RouterLink } from '@angular/router';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { SpinnerService } from '../../../service/ui/spinner/spinner.service';
import { finalize } from 'rxjs';
import { MatButtonModule } from '@angular/material/button';
import { MatError } from '@angular/material/form-field';
import { ButtonV2 } from "../../../shared/component-v2/button/button";

@Component({
  selector: 'app-login-v2',
  imports: [FormInput, ReactiveFormsModule, RouterLink, MatButtonModule, MatError, ButtonV2],
  templateUrl: './login-v2.html',
  styleUrl: './login-v2.css',
})
export class LoginV2 {
  form!: FormGroup;

  constructor(private readonly formBuilder: FormBuilder, private readonly authService: AuthService,
    private readonly router: Router, private readonly spinner: SpinnerService
  ) {
    this.form = this.formBuilder.group({
      username: ['', [CustomValidators.required("user.username.required")]],
      password: ['', [CustomValidators.required("user.password.required")]],
    });
  }

  get usernameControl() { return this.form.get('username') as FormControl; }
  get passwordControl() { return this.form.get('password') as FormControl; }

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
    this.router.navigateByUrl('/v2/register');
  }
}
