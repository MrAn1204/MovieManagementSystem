import { Component, input } from '@angular/core';
import { AbstractControl } from '@angular/forms';
import { MessageService } from '../../../../service/message.service';

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

    const error: ErrorMessageModel | string = this.control().getError(errorKey);

    if (typeof error === 'string') {
      console.log(error);
      return error;
    }

    return this.messageService.get(error.message, error.args);
  }
}

interface ErrorMessageModel {
  message: string;
  args?: Record<string, any>;
}

