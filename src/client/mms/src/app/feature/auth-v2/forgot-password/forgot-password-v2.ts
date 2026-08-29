import { Component, OnInit } from '@angular/core';
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
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

@Component({
  selector: 'app-forgot-password-v2',
  imports: [FormInput, ReactiveFormsModule, MatButtonModule, MatError],
  templateUrl: './forgot-password-v2.html',
  styleUrl: './forgot-password-v2.css',
})
export class ForgotPasswordV2 implements OnInit {
  form!: FormGroup;

  constructor(private readonly formBuilder: FormBuilder,
    private readonly authService: AuthService,
    private readonly spinner: SpinnerService,
    private readonly notification: NotificationDialogService,
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
    const dialogData: NotificationDialogDataModel = {
      type: 'success',
      message: "A password reset link has been sent to your email. The link will expire in 15 minutes.",
    };

    this.notification.openDialog(dialogData);
  }

  navigateToLogin(): void {
    this.router.navigateByUrl('/login');
  }

  get emailControl() { return this.form.get('email') as FormControl; }
}
