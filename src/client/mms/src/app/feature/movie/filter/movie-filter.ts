import { Component } from '@angular/core';
import { SelectField } from "../../../shared/component/form/select/select-field";
import { MultiselectField } from "../../../shared/component/form/multiselect/multiselect-field";
import { InputField } from "../../../shared/component/form/input/input-field";

@Component({
  selector: 'app-movie-filter',
  imports: [SelectField, MultiselectField, InputField],
  templateUrl: './movie-filter.html',
  styleUrl: './movie-filter.css',
})
export class MovieFilter {

}
