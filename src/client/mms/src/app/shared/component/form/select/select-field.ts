import { Component, input } from '@angular/core';

@Component({
  selector: 'app-select-field',
  imports: [],
  templateUrl: './select-field.html',
  styleUrl: './select-field.css',
})
export class SelectField {
  labelText= input<string>('');
  idName = input.required<string>();

  options: { label: string; value: string }[] = [
    { label: 'Option 1', value: 'option1' },
    { label: 'Option 2', value: 'option2' },
    { label: 'Option 3', value: 'option3' },
  ];
}
