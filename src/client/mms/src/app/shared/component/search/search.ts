import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { FormOptionModel } from '../../model/form-option.model';
import { Filter } from "../filter/filter";

@Component({
  selector: 'app-search',
  imports: [ReactiveFormsModule, Filter],
  templateUrl: './search.html',
  styleUrl: './search.css',
})
export class Search {
  constructor() { }

  filterVisible: boolean = false;
  ascending: boolean = true;
  
  sortOptions: FormOptionModel[] = [];
  searchForm = new FormGroup({
    keyword: new FormControl(''),
    sortBy: new FormControl(''),
    sortDirection: new FormControl('ASC'),
    pageNumber: new FormControl(1),
    pageSize: new FormControl(10),
  });

  toggleFilter(): void {
    this.filterVisible = !this.filterVisible;
  }

  toggleOrder(): void {
    this.ascending = !this.ascending;
  }

  onSubmit(): void {
    this.searchForm.controls.sortDirection.setValue(this.ascending ? 'ASC' : 'DESC');
    console.log(this.searchForm.value);
  }
}
