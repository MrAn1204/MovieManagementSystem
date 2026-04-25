import { Component, effect, forwardRef, input } from '@angular/core';
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
  keyword = '';
  displayValue = '';

  constructor() {
    super();

    effect(() => {
      this.displayValue = this.options().find(option => option.value === this.value)?.label || '';
    });
  }

  updateSelected(option: FormOptionModel): void {
    if (this.value === option.value) {
      this.updateValue('');
      this.displayValue = '';
    } else {
      this.updateValue(option.value);
      this.displayValue = option.label;
    }
  }

  resetSelected(): void {
    this.writeValue('');
    this.displayValue = '';
  }

  updateKeyword(value: string): void {
    this.keyword = value;
  }

  containKeyword(string: string): boolean {
    return string.toLowerCase().includes(this.keyword.toLowerCase());
  }
}
