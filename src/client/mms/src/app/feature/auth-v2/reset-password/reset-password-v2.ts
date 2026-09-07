import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { ConstraintService } from '../../../service/constraint.service';
import { SpinnerService } from '../../../service/ui/spinner/spinner.service';
import { AuthService } from '../../../service/auth/auth.service';
import { finalize } from 'rxjs';
import { ErrorRespondModel } from '../../../shared/model/error-respond.model';
import { FormMapper } from '../../../shared/util/form-mapper';
import { ActivatedRoute, Router } from '@angular/router';
import { PasswordResetFormModel } from '../../../model/form/password-reset-form.model';
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { MatButtonModule } from '@angular/material/button';
import { MatError } from '@angular/material/form-field';
import { NotificationDialogDataModel } from '../../../shared/model/dialog/notification-dialog-data.model';
import { NotificationDialogService } from '../../../service/dialog-v2/notification/notification-dialog.service';
import { ButtonV2 } from "../../../shared/component-v2/button/button";

@Component({
  selector: 'app-reset-password-v2',
  imports: [ReactiveFormsModule, FormInput, MatButtonModule, MatError, ButtonV2],
  templateUrl: './reset-password-v2.html',
  styleUrl: './reset-password-v2.css',
})
export class ResetPasswordV2 {
  form = this.buildForm();

  constructor(
    private readonly spinner: SpinnerService,
    private readonly authService: AuthService,
    private readonly router: Router,
    private readonly notification: NotificationDialogService,
    private readonly route: ActivatedRoute
  ) { }

  private buildForm() {
    const formBuilder = inject(FormBuilder);
    const constraintService = inject(ConstraintService);

    const passwordMin = constraintService.getConstraint('PASSWORD_MIN');

    return formBuilder.nonNullable.group({
      password: ['', [
        CustomValidators.required("user.password.required"),
        CustomValidators.passwordValid(passwordMin, "user.password.invalid")
      ]],
      confirmPassword: ['', [
        CustomValidators.required("user.confirmPassword.required")
      ]],
    }, { validators: CustomValidators.passwordMatch("user.password.mismatched") });
  }

  onSubmit(): void {
    this.form.markAllAsTouched();
    if (this.form.invalid) {
      return;
    }

    const data: PasswordResetFormModel = {
      token: this.route.snapshot.queryParamMap.get('token') || '',
      password: this.form.controls.password.value,
      confirmPassword: this.form.controls.confirmPassword.value,
    };

    this.spinner.show();

    this.authService.resetPassword(data)
      .pipe(finalize(() => this.spinner.hide()))
      .subscribe({
        next: () => this.displaySuccessDialog(),
        error: (res: ErrorRespondModel) => {
          FormMapper.mapErrorResponse(res, this.form);
          this.form.markAllAsTouched();
        }
      });
  }

  displaySuccessDialog(): void {
    const dialogData: NotificationDialogDataModel = {
      type: 'success',
      message: 'Your password has been reset successfully. You can now log in with your new password.'
    };

    const ref = this.notification.openDialog(dialogData);

    ref.afterClosed().subscribe(() => this.navigateToLogin());
  }

  navigateToLogin(): void {
    this.router.navigateByUrl('/v2/login');
  }
}
