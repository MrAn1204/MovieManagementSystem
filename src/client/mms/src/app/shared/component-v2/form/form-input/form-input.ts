import { Component, input } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormField } from '../form-field/form-field';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-input',
  imports: [MatInputModule, MatFormFieldModule, ReactiveFormsModule],
  templateUrl: './form-input.html',
  styleUrl: './form-input.css',
})
export class FormInput extends FormField {
  type = input<FormInputType>('text');
  placeholder = input<string>('');
}

type FormInputType =
  | 'text'
  | 'email'
  | 'password'
  | 'number'
  | 'tel'
  | 'url'
  | 'date'
  | 'datetime-local'
  | 'month'
  | 'search'
  | 'time'
  | 'week'
  | 'color';
