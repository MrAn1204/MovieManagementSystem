import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { ConstraintService } from '../../../service/constraint.service';
import { ValidationError } from "../../../shared/component/form/error/validation-error";
import { Button } from "../../../shared/component/button/button";
import { SpinnerService } from '../../../service/ui/spinner/spinner.service';
import { AuthService } from '../../../service/auth/auth.service';
import { finalize } from 'rxjs';
import { ErrorRespondModel } from '../../../shared/model/error-respond.model';
import { FormMapper } from '../../../shared/util/form-mapper';
import { ActivatedRoute, Router } from '@angular/router';
import { DialogService } from '../../../service/dialog/dialog.service';
import { DialogPopupDataModel } from '../../../shared/model/dialog/dialog-popup-data.model';
import { PopupModal } from '../../../shared/component/dialog/popup-modal/popup-modal';
import { InputField } from "../../../shared/component/form/input/input-field";
import { PasswordResetFormModel } from '../../../model/form/password-reset-form.model';

@Component({
  selector: 'app-reset-password',
  imports: [ReactiveFormsModule, ValidationError, Button, InputField],
  templateUrl: './reset-password.html',
  styleUrl: './reset-password.css',
})
export class ResetPassword implements OnInit {
  form!: FormGroup;

  constructor(
    private readonly formBuilder: FormBuilder,
    private readonly constraintService: ConstraintService,
    private readonly spinner: SpinnerService,
    private readonly authService: AuthService,
    private readonly router: Router,
    private readonly dialog: DialogService,
    private readonly route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const passwordMin = this.constraintService.getConstraint('PASSWORD_MIN');

    this.form = this.formBuilder.group({
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
      password: this.form.get('password')!.value,
      confirmPassword: this.form.get('confirmPassword')!.value,
    }

    this.spinner.show();

    this.authService.resetPassword(data)
      .pipe(finalize(() => this.spinner.hide()))
      .subscribe({
        next: () => this.displaySuccessDialog(),
        error: (res: ErrorRespondModel) => {
          console.log(res);
          FormMapper.mapErrorResponse(res, this.form);
            console.log(this.form.errors);

            this.form.markAllAsTouched();
          }
      });
  }

  displaySuccessDialog(): void {
    const dialogData: DialogPopupDataModel = {
      type: 'success',
      message: 'Your password has been reset successfully. You can now log in with your new password.'
    }

    const ref = this.dialog.openDialog(PopupModal, dialogData);

    ref.closed.subscribe(() => this.navigateToLogin());
  }

  navigateToLogin(): void {
    this.router.navigateByUrl('/login');
  }
}
