import { Component, input, OnInit, Type } from '@angular/core';
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
  layoutFilter = input<Type<unknown>>();

  sortOptions: FormOptionModel[] = [];
  form!: FormGroup;

  constructor() { }

  ngOnInit(): void {
    this.form = new FormGroup({
      keyword: new FormControl(''),
      sortBy: new FormControl(''),
      sortDirection: new FormControl('ASC'),
      pageNumber: new FormControl(1),
      pageSize: new FormControl(10),
    });
  }

  toggleFilter(): void {
    this.filterVisible = !this.filterVisible;
  }

  toggleOrder(): void {
    this.ascending = !this.ascending;
  }

  onSubmit(): void {
    this.form.controls['sortDirection'].setValue(this.ascending ? 'ASC' : 'DESC');
    console.log(this.form.value);
  }
}
