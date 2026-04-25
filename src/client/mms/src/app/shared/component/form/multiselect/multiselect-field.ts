import { Component, effect, forwardRef, input } from '@angular/core';
import { BaseField } from '../base-field/base-field';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { FormOptionModel } from '../../../model/form-option.model';

@Component({
  selector: 'app-multiselect-field',
  imports: [],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: forwardRef(() => MultiselectField)
    },
  ],
  templateUrl: './multiselect-field.html',
  styleUrl: './multiselect-field.css',
})
export class MultiselectField extends BaseField<string[]> {
  options = input<FormOptionModel[]>([]);
  selected: string[] = [];
  keyword = '';

  constructor() {
    super();

    effect(() => {
      this.selected = this.options().filter(option => this.value?.includes(option.value)).map(option => option.label);
    });
  }

  override writeValue(value: string[]): void {
    super.writeValue(value);

    if (!value || value.length === 0) {
      this.selected = [];
    }
  }

  updateSelected(option: FormOptionModel): void {
    let newValue = this.value ? [...this.value] : [];

    if (newValue.includes(option.value)) {
      newValue = newValue.filter(item => item !== option.value);
      this.selected = this.selected.filter(item => item !== option.label);
    } else {
      newValue.push(option.value);
      this.selected.push(option.label);
    }

    this.updateValue(newValue);
  }

  resetSelected(): void {
    this.writeValue([]);
  }

  updateKeyword(value: string): void {
    this.keyword = value;
  }

  containKeyword(string: string): boolean {
    return string.toLowerCase().includes(this.keyword.toLowerCase());
  }
}
