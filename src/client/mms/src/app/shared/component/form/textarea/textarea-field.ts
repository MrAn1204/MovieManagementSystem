import { Component, input } from '@angular/core';
import { BaseField } from '../base-field/base-field';

@Component({
  selector: 'app-textarea-field',
  imports: [],
  templateUrl: './textarea-field.html',
  styleUrl: './textarea-field.css',
})
export class Textarea extends BaseField<string> {
  rowSize = input<number>(5);
}
