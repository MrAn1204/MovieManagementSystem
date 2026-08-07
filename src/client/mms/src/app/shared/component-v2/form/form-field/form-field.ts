import { Component, input } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MessageService } from '../../../../service/message.service';

@Component({
  selector: 'app-form-field',
  imports: [],
  templateUrl: './form-field.html',
  styleUrl: './form-field.css',
})
export abstract class FormField {
  control = input.required<FormControl>();
  idName = input.required<string>();
  label = input<string>();
  required = input<boolean>(false);

  constructor(private readonly messageService: MessageService) { }

  disable() {
    this.control().disable();
  }

  enable() {
    this.control().enable();
  }

  get error() {
    const errors = this.control().errors;

    if (!errors) {
      return null;
    }

    const errorKey = Object.keys(errors)[0];
    const messageKey = this.control().getError(errorKey);

    return this.messageService.get(messageKey);
  }
}
