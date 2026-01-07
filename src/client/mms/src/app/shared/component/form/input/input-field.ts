import { Component, forwardRef, input } from '@angular/core';
import { BaseField } from '../base-field/base-field';
import { NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-input-field',
  imports: [],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: forwardRef(() => InputField)
    },
  ],
  templateUrl: './input-field.html',
  styleUrl: './input-field.css',
})
export class InputField extends BaseField<string> {
  inputType = input.required<string>();
  placeholderText = input<string>('');
}
