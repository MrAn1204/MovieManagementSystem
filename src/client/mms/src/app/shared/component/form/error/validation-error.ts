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

  get errorMessage(): string | null {
    const error = Object.keys({ ...this.control().errors })[0];

    return this.control().getError(error);
  }
}
