import { Component, input } from '@angular/core';
import { BaseField } from '../base-field/base-field';
import { FormOptionModel } from '../../../model/form-option.model';

@Component({
  selector: 'app-select-field',
  imports: [],
  templateUrl: './select-field.html',
  styleUrl: './select-field.css',
})
export class SelectField extends BaseField<string> {
  options = input<FormOptionModel[]>([]);
}
