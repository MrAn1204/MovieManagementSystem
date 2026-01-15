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
      this.selected = this.options().filter(option => option.selected).map(option => option.label);
    });
  }

  updateSelected(option: FormOptionModel): void {
    let newValue = this.value ? [...this.value] : [];
    option.selected = !option.selected;

    if (option.selected) {
      newValue.push(option.value);
      this.selected.push(option.label);
    } else {
      newValue = newValue.filter(item => item !== option.value);
      this.selected = this.selected.filter(item => item !== option.label);
    }

    this.updateValue(newValue);
  }

  resetSelected(): void {
    this.selected = [];
    this.updateValue([]);
    this.options()?.forEach(item => item.selected = false);
  }

  updateKeyword(value: string): void {
    this.keyword = value;
  }

  containKeyword(string: string): boolean {
    return string.toLowerCase().includes(this.keyword.toLowerCase());
  }
}
