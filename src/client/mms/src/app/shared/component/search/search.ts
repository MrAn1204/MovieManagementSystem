import { Component, input, output, Type } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { FormOptionModel } from '../../model/form-option.model';
import { NgComponentOutlet } from '@angular/common';

@Component({
  selector: 'app-search',
  imports: [ReactiveFormsModule, NgComponentOutlet],
  templateUrl: './search.html',
  styleUrl: './search.css',
})
export class Search {
  filterVisible: boolean = false;
  ascending: boolean = true;

  form = input.required<FormGroup>();
  contentFilter = input<Type<unknown>>();
  sortOptions = input<FormOptionModel[]>();

  triggerSearch = output<void>();


  constructor() { }

  toggleFilter(): void {
    this.filterVisible = !this.filterVisible;
  }

  toggleOrder(): void {
    this.ascending = !this.ascending;
    this.form().controls['sortDirection'].setValue(this.ascending ? 'ASC' : 'DESC');
  }

  reset(): void {
    this.form().reset();
    this.ascending = true;
  }

  onSubmit(): void {
    this.triggerSearch.emit();
  }
}
