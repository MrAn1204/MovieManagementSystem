import { Component, input } from '@angular/core';

@Component({
  selector: 'app-multiselect-field',
  imports: [],
  templateUrl: './multiselect-field.html',
  styleUrl: './multiselect-field.css',
})
export class MultiselectField {
  labelText = input<string>('');
  idName = input.required<string>();

  data: { id: string, name: string, checked: boolean }[] = [];
  selected: string[] = [];
  fieldValue: string[] = [];
  keyword = '';

  constructor() {
    for (let i = 1; i <= 7; i++) {
      this.data.push({ id: `option${i}`, name: `Option ${i}`, checked: false });
    }
  }

  updateSelected(value: { id: string, name: string, checked: boolean }, event: Event): void {
    const checked = (event.target as HTMLInputElement).checked;

    value.checked = checked;

    if (checked) {
      this.fieldValue.push(value.id);
      this.selected.push(value.name);
    } else {
      this.selected = this.selected.filter(item => item !== value.name);
      this.fieldValue = this.fieldValue.filter(item => item !== value.id);
    }
  }

  resetSelected(): void {
    this.selected = [];
    this.fieldValue = [];
    this.data.forEach(item => item.checked = false);
  }

  updateKeyword(value: string): void {
    this.keyword = value;
  }

  containKeyword(string: string): boolean {
    return string.toLowerCase().includes(this.keyword.toLowerCase());
  }
}
