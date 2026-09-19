import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { AuthService } from '../../../service/auth/auth.service';
import { Router, RouterLink } from '@angular/router';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { SpinnerService } from '../../../service/ui/spinner/spinner.service';
import { finalize } from 'rxjs';
import { MatButtonModule } from '@angular/material/button';
import { MatError } from '@angular/material/form-field';
import { ButtonV2 } from "../../../shared/component-v2/button/button";
import { LoginRequest } from '../../../model/auth/login-request';
import { FormMapper } from '../../../shared/util/form-mapper';
import { ErrorRespondModel } from '../../../shared/model/error-respond.model';

@Component({
  selector: 'app-login-v2',
  imports: [FormInput, ReactiveFormsModule, RouterLink, MatButtonModule, MatError, ButtonV2],
  templateUrl: './login-v2.html',
  styleUrl: './login-v2.css',
})
export class LoginV2 {
  private readonly formBuilder = inject(FormBuilder);

  form = this.formBuilder.group({
    username: ['', [CustomValidators.required("user.username.required")]],
    password: ['', [CustomValidators.required("user.password.required")]],
  });

  constructor(private readonly authService: AuthService,
    private readonly router: Router, private readonly spinner: SpinnerService
  ) { }

  onSubmit() {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      this.spinner.show();
      this.authService.login(this.form.getRawValue() as LoginRequest)
        .pipe(finalize(() => this.spinner.hide()))
        .subscribe({
          error: (res: ErrorRespondModel) => FormMapper.mapErrorResponse(res, this.form)
        });
    }
  }

  navigateToRegister() {
    this.router.navigateByUrl('/register');
  }
}
