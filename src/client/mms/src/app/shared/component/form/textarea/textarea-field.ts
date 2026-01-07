import { Component, forwardRef, input } from '@angular/core';
import { BaseField } from '../base-field/base-field';
import { NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-textarea-field',
  imports: [],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: forwardRef(() => Textarea)
    },
  ],
  templateUrl: './textarea-field.html',
  styleUrl: './textarea-field.css',
})
export class Textarea extends BaseField<string> {
  rowSize = input<number>(5);
}
