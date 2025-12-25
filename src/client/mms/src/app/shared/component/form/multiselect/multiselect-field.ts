import { Component, input } from '@angular/core';
import { BaseField } from '../base-field/base-field';

@Component({
  selector: 'app-multiselect-field',
  imports: [],
  templateUrl: './multiselect-field.html',
  styleUrl: './multiselect-field.css',
})
export class MultiselectField extends BaseField<string[]> {
  options = input<{ id: string, name: string, checked: boolean }[]>([]);
  selected: string[] = [];
  keyword = '';

  constructor() {
    super();
    for (let i = 1; i <= 7; i++) {
      this.options().push({ id: `option${i}`, name: `Option ${i}`, checked: false });
    }
  }

  updateSelected(option: { id: string, name: string, checked: boolean }): void {
    let newValue = this.value ? [...this.value] : [];
    option.checked = !option.checked;

    if (option.checked) {
      newValue.push(option.id);
      this.selected.push(option.name);
    } else {
      newValue = newValue.filter(item => item !== option.id);
      this.selected = this.selected.filter(item => item !== option.name);
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
