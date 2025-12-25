import { Component, input } from '@angular/core';
import { BaseField } from '../base-field/base-field';

@Component({
  selector: 'app-select-field',
  imports: [],
  templateUrl: './select-field.html',
  styleUrl: './select-field.css',
})
export class SelectField extends BaseField<string> {
  options = input<{ label: string; value: string }[]>([
    { label: 'Option 1', value: 'option1' },
    { label: 'Option 2', value: 'option2' },
    { label: 'Option 3', value: 'option3' },
  ]);
}
