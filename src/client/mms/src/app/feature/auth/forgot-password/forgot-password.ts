import { Component, OnInit } from '@angular/core';
import { InputField } from "../../../shared/component/form/input/input-field";
import { ValidationError } from "../../../shared/component/form/error/validation-error";
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { AuthService } from '../../../service/auth/auth.service';
import { SpinnerService } from '../../../service/ui/spinner/spinner.service';
import { finalize } from 'rxjs';
import { ErrorRespondModel } from '../../../shared/model/error-respond.model';
import { DialogService } from '../../../service/dialog/dialog.service';
import { PopupModal } from '../../../shared/component/dialog/popup-modal/popup-modal';
import { Button } from "../../../shared/component/button/button";
import { DialogPopupDataModel } from '../../../shared/model/dialog/dialog-popup-data.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-forgot-password',
  imports: [InputField, ValidationError, ReactiveFormsModule, Button],
  templateUrl: './forgot-password.html',
  styleUrl: './forgot-password.css',
})
export class ForgotPassword implements OnInit {
  form!: FormGroup;

  constructor(private readonly formBuilder: FormBuilder,
    private readonly authService: AuthService,
    private readonly spinner: SpinnerService,
    private readonly dialogService: DialogService,
    private readonly router: Router
  ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      email: ['', [CustomValidators.required("user.email.required"), CustomValidators.email("user.email.invalid")]],
    });
  }

  onSubmit(): void {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      const email = this.form.get('email')?.value;
      this.spinner.show();

      this.authService.forgotPassword(email)
        .pipe(finalize(() => this.spinner.hide()))
        .subscribe({
          next: () => this.displaySuccess(),
          error: (res: ErrorRespondModel) => {
            this.form.setErrors({ serverError: res.messages[Object.keys(res.messages)[0]] || "An error occurred" });
            this.form.markAllAsTouched();
          }
        });
    }
  }

  displaySuccess(): void {
    const dialogData: DialogPopupDataModel = {
      type: 'success',
      message: "A password reset link has been sent to your email. The link will expire in 15 minutes.",
    }

    this.dialogService.openDialog(PopupModal, dialogData);
  }

  navigateToLogin(): void {
    this.router.navigateByUrl('/login');
  }
}
