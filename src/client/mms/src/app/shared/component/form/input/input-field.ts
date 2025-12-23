import { Component, input } from '@angular/core';

@Component({
  selector: 'app-input-field',
  imports: [],
  templateUrl: './input-field.html',
  styleUrl: './input-field.css',
})
export class InputField {
  labelText = input<string>('');
  idName = input.required<string>();
  inputType = input.required<string>();
  placeholderText = input<string>('');
}
