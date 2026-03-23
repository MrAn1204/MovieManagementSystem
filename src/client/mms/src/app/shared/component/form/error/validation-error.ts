import { Component, input } from '@angular/core';
import { AbstractControl } from '@angular/forms';
import { MessageService } from '../../../../service/message.service';
import { ErrorMessageModel } from '../../../model/error-message.model';

@Component({
  selector: 'app-validation-error',
  imports: [],
  templateUrl: './validation-error.html',
  styleUrl: './validation-error.css',
})
export class ValidationError {
  control = input.required<AbstractControl>();

  constructor(private readonly messageService: MessageService) { }

  get errorMessage(): string | null {
    const errorKey = Object.keys({ ...this.control().errors })[0];

    if (!errorKey) {
      return null;
    }

    const error: ErrorMessageModel | string = this.control().getError(errorKey);

    return this.messageService.get(error);
  }
}
