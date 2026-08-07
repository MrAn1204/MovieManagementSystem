import { Component, input } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormField } from '../form-field/form-field';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-textarea',
  imports: [MatInputModule, MatFormFieldModule, ReactiveFormsModule],
  templateUrl: './form-textarea.html',
  styleUrl: './form-textarea.css',
})
export class FormTextArea extends FormField {
  placeholder = input<string>('');
  rowSize = input<number>(5)
}
