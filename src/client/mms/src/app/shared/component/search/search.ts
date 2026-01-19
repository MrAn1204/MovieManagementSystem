import { Component, input, OnInit, output, Type } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { FormOptionModel } from '../../model/form-option.model';
import { NgComponentOutlet } from '@angular/common';

@Component({
  selector: 'app-search',
  imports: [ReactiveFormsModule, NgComponentOutlet],
  templateUrl: './search.html',
  styleUrl: './search.css',
})
export class Search implements OnInit {
  filterVisible: boolean = false;
  ascending: boolean = true;

  form = input.required<FormGroup>();
  contentFilter = input<Type<unknown>>();
  sortOptions = input<FormOptionModel[]>();

  triggerSearch = output<void>();

  initialFields: Record<string, FormControl> = {
    keyword: new FormControl(''),
    sortBy: new FormControl('id'),
    sortDirection: new FormControl('ASC'),
    pageNumber: new FormControl(1),
    pageSize: new FormControl(10),
  };

  constructor() { }

  ngOnInit(): void {
    Object.entries(this.initialFields).forEach(([key, control]) => {
      this.form().addControl(key, control);
    });
    
    this.triggerSearch.emit(this.form().value);
  }

  toggleFilter(): void {
    this.filterVisible = !this.filterVisible;
  }

  toggleOrder(): void {
    this.ascending = !this.ascending;
  }

  onSubmit(): void {
    this.form().controls['sortDirection'].setValue(this.ascending ? 'ASC' : 'DESC');
    this.triggerSearch.emit();
  }
}
