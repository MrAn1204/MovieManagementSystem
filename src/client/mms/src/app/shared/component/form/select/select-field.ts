import { Component, forwardRef, input } from '@angular/core';
import { BaseField } from '../base-field/base-field';
import { FormOptionModel } from '../../../model/form-option.model';
import { NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-select-field',
  imports: [],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: forwardRef(() => SelectField)
    },
  ],
  templateUrl: './select-field.html',
  styleUrl: './select-field.css',
})
export class SelectField extends BaseField<string> {
  options = input<FormOptionModel[]>([]);
}
