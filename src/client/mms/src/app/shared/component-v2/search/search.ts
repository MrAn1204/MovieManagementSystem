import { Component, input, output } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { FormOptionModel } from '../../model/form-option.model';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from "@angular/material/button";
import { MatButtonToggleModule } from "@angular/material/button-toggle";
import { MatTooltip } from "@angular/material/tooltip";

@Component({
  selector: 'app-search-v2',
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatIconModule, MatSelectModule, MatButtonModule, MatButtonToggleModule, MatTooltip],
  templateUrl: './search.html',
  styleUrl: './search.css',
})
export class SearchV2 {
  form = input.required<FormGroup<SearchForm>>();

  sortOptions = input<FormOptionModel[]>();

  submitForm = output<void>();

  resetForm = output<void>();

  filterVisible: boolean = false;

  get sortOrder(): 'ASC' | 'DESC' {
    return this.form().controls.sortDirection.value;
  }

  toggleFilters() {
    this.filterVisible = !this.filterVisible;
  }

  submit() {
    this.filterVisible = false;
    this.submitForm.emit();
  }

  reset() {
    this.resetForm.emit();
  }
}

export type SearchForm = {
keyword: FormControl<string>;
  sortBy: FormControl<string>;
  sortDirection: FormControl<'ASC' | 'DESC'>;
  pageNumber: FormControl<number>;
  pageSize: FormControl<number>;
}
