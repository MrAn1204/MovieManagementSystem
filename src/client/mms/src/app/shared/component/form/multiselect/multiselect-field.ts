import { Component, forwardRef, input } from '@angular/core';
import { BaseField } from '../base-field/base-field';
import { CheckboxOptionModel } from '../../../model/checkbox-option.model';
import { NG_VALUE_ACCESSOR } from '@angular/forms';

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
  options = input<CheckboxOptionModel[]>([]);
  selected: string[] = [];
  keyword = '';

  updateSelected(option: CheckboxOptionModel): void {
    let newValue = this.value ? [...this.value] : [];
    option.checked = !option.checked;

    if (option.checked) {
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
    super.updateValue([]);
    this.options().forEach(item => item.checked = false);
  }

  updateKeyword(value: string): void {
    this.keyword = value;
  }

  containKeyword(string: string): boolean {
    return string.toLowerCase().includes(this.keyword.toLowerCase());
  }
}
