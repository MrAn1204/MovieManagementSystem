import { Component, inject, OnInit } from '@angular/core';
import { SelectField } from "../../../shared/component/form/select/select-field";
import { MultiselectField } from "../../../shared/component/form/multiselect/multiselect-field";
import { InputField } from "../../../shared/component/form/input/input-field";
import { ControlContainer, FormControl, FormGroup, FormGroupDirective } from '@angular/forms';

@Component({
  selector: 'app-movie-filter',
  imports: [SelectField, MultiselectField, InputField],
  viewProviders: [{ provide: ControlContainer, useExisting: FormGroupDirective }],
  templateUrl: './movie-filter.html',
  styleUrl: './movie-filter.css',
})
export class MovieFilter implements OnInit {
  form: FormGroup = inject(ControlContainer).control as FormGroup;

  fields: Record<string, FormControl> = {
    language: new FormControl(''),
    genre: new FormControl([]),
    studio: new FormControl([]),
    releaseAfter: new FormControl(''),
    releaseBefore: new FormControl(''),
  };

  ngOnInit(): void {
    Object.entries(this.fields).forEach(([key, control]) => {
      this.form.addControl(key, control);
    });
  }
}
