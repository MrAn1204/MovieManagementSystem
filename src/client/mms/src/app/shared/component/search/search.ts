import { Component, input, output } from '@angular/core';
import { SelectField } from '../form/select/select-field';
import { InputField } from '../form/input/input-field';
import { MultiselectField } from '../form/multiselect/multiselect-field';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-search',
  imports: [SelectField, InputField, MultiselectField, ReactiveFormsModule],
  templateUrl: './search.html',
  styleUrl: './search.css',
})
export class Search{
  constructor() { }
  
  visible = input<boolean>();
  
  toggleFilter = output<void>();
  
  ascending = true;
  sortOptions = [
    { label: 'Option 1', value: 'option1' },
    { label: 'Option 2', value: 'option2' },
    { label: 'Option 3', value: 'option3' },
  ];
  searchForm = new FormGroup({
    keyword: new FormControl(''),
    sortBy: new FormControl(''),
    sortDirection: new FormControl('ASC'),
    pageNumber: new FormControl(1),
    pageSize: new FormControl(10),
  });

  toggleOrder(): void {
    this.ascending = !this.ascending;
  }
}
