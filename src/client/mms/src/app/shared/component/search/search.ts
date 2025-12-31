import { Component, input } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { FormOptionModel } from '../../model/form-option.model';

@Component({
  selector: 'app-search',
  imports: [ReactiveFormsModule],
  templateUrl: './search.html',
  styleUrl: './search.css',
})
export class Search {
  filterVisible: boolean = false;
  ascending: boolean = true;
  filters = input<Record<string, FormControl>>();

  sortOptions: FormOptionModel[] = [];
  searchForm: FormGroup;

  constructor() {
    this.searchForm = new FormGroup({
      keyword: new FormControl(''),
      sortBy: new FormControl(''),
      sortDirection: new FormControl('ASC'),
      pageNumber: new FormControl(1),
      pageSize: new FormControl(10),
      ...this.filters(),
    });
  }

  toggleFilter(): void {
    this.filterVisible = !this.filterVisible;
  }

  toggleOrder(): void {
    this.ascending = !this.ascending;
  }

  onSubmit(): void {
    this.searchForm.controls['sortDirection'].setValue(this.ascending ? 'ASC' : 'DESC');
    console.log(this.searchForm.value);
  }
}
