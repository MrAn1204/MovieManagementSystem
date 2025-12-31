import { Component } from '@angular/core';
import { InputField } from "../../../shared/component/form/input/input-field";
import { ImageField } from "../../../shared/component/form/image/image-field";
import { SelectField } from "../../../shared/component/form/select/select-field";
import { MultiselectField } from "../../../shared/component/form/multiselect/multiselect-field";
import { Textarea } from "../../../shared/component/form/textarea/textarea-field";

@Component({
  selector: 'app-movie-create-edit',
  imports: [InputField, ImageField, SelectField, MultiselectField, Textarea],
  templateUrl: './movie-create-edit.html',
  styleUrl: './movie-create-edit.css',
})
export class MovieCreateEdit {

}
