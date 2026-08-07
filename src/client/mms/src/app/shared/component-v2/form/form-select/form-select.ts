import { Component, input } from '@angular/core';
import { FormField } from '../form-field/form-field';
import { FormOptionModel } from '../../../model/form-option.model';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-select',
  imports: [MatSelectModule, MatFormFieldModule, ReactiveFormsModule],
  templateUrl: './form-select.html',
  styleUrl: './form-select.css',
})
export class FormSelect extends FormField {
  options = input.required<FormOptionModel[]>();
  isMultiple = input<boolean>(false);

  keyword: string = '';

  onSearch(keyword: string) {
    this.keyword = keyword.toLowerCase();
  }

  isMatched(option: string) {
    return option.toLowerCase().includes(this.keyword);
  }

  clearSelected() {
    this.control().reset();
  }
}
