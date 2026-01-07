import { Component, inject, input, OnInit } from '@angular/core';
import { InputField } from "../../../shared/component/form/input/input-field";
import { ImageField } from "../../../shared/component/form/image/image-field";
import { SelectField } from "../../../shared/component/form/select/select-field";
import { MultiselectField } from "../../../shared/component/form/multiselect/multiselect-field";
import { Textarea } from "../../../shared/component/form/textarea/textarea-field";
import { MovieModel } from '../../../model/movie.model';
import { ControlContainer, FormControl, FormGroup, FormGroupDirective, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-movie-create-edit',
  imports: [InputField, ImageField, SelectField, MultiselectField, Textarea, ReactiveFormsModule],
  viewProviders: [{ provide: ControlContainer, useExisting: FormGroupDirective }],
  templateUrl: './movie-create-edit.html',
  styleUrl: './movie-create-edit.css',
})
export class MovieCreateEdit implements OnInit {
  model = input<MovieModel>();
  mode = input<'create' | 'edit'>();

  form: FormGroup = inject(ControlContainer).control as FormGroup;

  fields: Record<string, FormControl> = {
    name: new FormControl(this.model()?.name || ''),
    releaseDate: new FormControl(this.model()?.releaseDate || ''),
    duration: new FormControl(this.model()?.duration || 0),
    content: new FormControl(this.model()?.content || ''),
    thumbnail: new FormControl(this.model()?.thumbnail || ''),
    genre: new FormControl(this.model()?.genres || []),
    studio: new FormControl(this.model()?.studios || []),
    talent: new FormControl(this.model()?.talents || []),
    language: new FormControl(this.model()?.language || ''),
  };

  ngOnInit(): void {
    Object.entries(this.fields).forEach(([key, control]) => {
      this.form.addControl(key, control);
    });
  }
}
