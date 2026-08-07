import { Component, input } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-filter',
  imports: [],
  templateUrl: './filter.html',
  styleUrl: './filter.css',
})
export abstract class Filter<T extends { [key: string]: FormControl }> {
  form = input.required<FormGroup<T>>();
}
