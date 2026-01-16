import { Component, inject, OnInit, signal } from '@angular/core';
import { SelectField } from "../../../shared/component/form/select/select-field";
import { MultiselectField } from "../../../shared/component/form/multiselect/multiselect-field";
import { InputField } from "../../../shared/component/form/input/input-field";
import { ControlContainer, FormControl, FormGroup, FormGroupDirective, ReactiveFormsModule } from '@angular/forms';
import { GenreService } from '../../../service/genre/genre.service';
import { StudioService } from '../../../service/studio/studio.service';
import { LanguageService } from '../../../service/language/language.service';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { FlowbiteService } from '../../../service/flowbite.service';

@Component({
  selector: 'app-movie-filter',
  imports: [SelectField, MultiselectField, InputField, ReactiveFormsModule],
  viewProviders: [{ provide: ControlContainer, useExisting: FormGroupDirective }],
  templateUrl: './movie-filter.html',
  styleUrl: './movie-filter.css',
})
export class MovieFilter implements OnInit {
  form: FormGroup = inject(ControlContainer).control as FormGroup;

  genres = signal<FormOptionModel[]>([]);
  studios = signal<FormOptionModel[]>([]);
  languages = signal<FormOptionModel[]>([]);

  fields: Record<string, FormControl> = {
    languageId: new FormControl(''),
    genreIds: new FormControl([]),
    studioIds: new FormControl([]),
    releaseAfter: new FormControl(''),
    releaseBefore: new FormControl(''),
  };

  constructor(
    private readonly genreService: GenreService,
    private readonly studioService: StudioService,
    private readonly languageService: LanguageService,
    private readonly flowbiteService: FlowbiteService
  ) { }

  ngOnInit(): void {
    this.flowbiteService.loadFlowbite((flowbite) => {
      flowbite.initFlowbite();
    });

    Object.entries(this.fields).forEach(([key, control]) => {
      this.form.addControl(key, control);
    });

    this.genreService.getAll().subscribe((genres) => {
      this.genres.set(genres.map((genre) => ({
        label: genre.name,
        value: genre.id,
        selected: false
      })));
    });

    this.studioService.getAll().subscribe((studios) => {
      this.studios.set(studios.map((studio) => ({
        label: studio.name,
        value: studio.id,
        selected: false
      })));
    });

    this.languageService.getAll().subscribe((languages) => {
      const languageOptions: FormOptionModel[] = languages.map((language) => ({
          label: language.name,
          value: language.id,
          selected: false
        }));

      languageOptions.unshift({
        label: 'Any Language',
        value: '',
        selected: true
      });

      this.languages.set(languageOptions);
    });
  }
}
