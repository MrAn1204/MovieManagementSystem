import { Component, inject } from '@angular/core';
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { AuthService } from '../../../service/auth/auth.service';
import { SpinnerService } from '../../../service/ui/spinner/spinner.service';
import { finalize } from 'rxjs';
import { ErrorRespondModel } from '../../../shared/model/error-respond.model';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { NotificationDialogDataModel } from '../../../shared/model/dialog/notification-dialog-data.model';
import { MatError } from '@angular/material/form-field';
import { NotificationDialogService } from '../../../service/dialog-v2/notification/notification-dialog.service';
import { FormMapper } from '../../../shared/util/form-mapper';
import { ButtonV2 } from "../../../shared/component-v2/button/button";

@Component({
  selector: 'app-forgot-password-v2',
  imports: [FormInput, ReactiveFormsModule, MatButtonModule, MatError, ButtonV2],
  templateUrl: './forgot-password-v2.html',
  styleUrl: './forgot-password-v2.css',
})
export class ForgotPasswordV2 {
  private readonly formBuilder = inject(FormBuilder);

  form = this.formBuilder.nonNullable.group({
    email: ['', [CustomValidators.required("user.email.required"), CustomValidators.email("user.email.invalid")]],
  });

  constructor(
    private readonly authService: AuthService,
    private readonly spinner: SpinnerService,
    private readonly notification: NotificationDialogService,
    private readonly router: Router
  ) { }

  onSubmit(): void {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      const email = this.form.controls.email.value;
      this.spinner.show();

      this.authService.forgotPassword(email)
        .pipe(finalize(() => this.spinner.hide()))
        .subscribe({
          next: () => this.displaySuccess(),
          error: (res: ErrorRespondModel) => FormMapper.mapErrorResponse(res, this.form)
        });
    }
  }

  displaySuccess(): void {
    const dialogData: NotificationDialogDataModel = {
      type: 'success',
      message: "A password reset link has been sent to your email. The link will expire in 15 minutes.",
    };

    this.notification.openDialog(dialogData);
  }

  navigateToLogin(): void {
    this.router.navigateByUrl('/v2/login');
  }
}
