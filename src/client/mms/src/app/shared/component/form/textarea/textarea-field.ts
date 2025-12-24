import { Component, input } from '@angular/core';

@Component({
  selector: 'app-textarea-field',
  imports: [],
  templateUrl: './textarea-field.html',
  styleUrl: './textarea-field.css',
})
export class Textarea {
  labelText = input<string>('');
  idName = input.required<string>();
  rowSize = input<number>(5);
}
