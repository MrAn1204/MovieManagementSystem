import { Component, input } from '@angular/core';
import { BaseField } from '../base-field/base-field';

@Component({
  selector: 'app-input-field',
  imports: [],
  templateUrl: './input-field.html',
  styleUrl: './input-field.css',
})
export class InputField extends BaseField<string> {
  inputType = input.required<string>();
  placeholderText = input<string>('');
}
