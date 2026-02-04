import { Component, input } from '@angular/core';
import { AbstractControl } from '@angular/forms';

@Component({
  selector: 'app-validation-error',
  imports: [],
  templateUrl: './validation-error.html',
  styleUrl: './validation-error.css',
})
export class ValidationError {
  control = input.required<AbstractControl>();
  errorMessages = input.required<Record<string, string>>();

  get errorMessage(): string | null {
    const error = Object.keys({ ...this.control().errors })[0];

    return this.errorMessages()[error] || this.control().getError(error);
  }
}
