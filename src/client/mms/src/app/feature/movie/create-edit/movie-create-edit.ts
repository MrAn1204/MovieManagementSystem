import { Component, inject, input, OnInit, signal } from '@angular/core';
import { InputField } from "../../../shared/component/form/input/input-field";
import { ImageField } from "../../../shared/component/form/image/image-field";
import { SelectField } from "../../../shared/component/form/select/select-field";
import { MultiselectField } from "../../../shared/component/form/multiselect/multiselect-field";
import { Textarea } from "../../../shared/component/form/textarea/textarea-field";
import { MovieModel } from '../../../model/movie.model';
import { ControlContainer, FormControl, FormGroup, FormGroupDirective, ReactiveFormsModule } from '@angular/forms';
import { GenreService } from '../../../service/genre/genre.service';
import { StudioService } from '../../../service/studio/studio.service';
import { TalentService } from '../../../service/talent/talent.service';
import { LanguageService } from '../../../service/language/language.service';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { CheckboxOptionModel } from '../../../shared/model/checkbox-option.model';

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

  genres = signal<CheckboxOptionModel[]>([]);
  studios = signal<CheckboxOptionModel[]>([]);
  talents = signal<CheckboxOptionModel[]>([]);
  languages = signal<FormOptionModel[]>([]);

  constructor(
    private readonly genreService: GenreService,
    private readonly studioService: StudioService,
    private readonly talentService: TalentService,
    private readonly languageService: LanguageService,
  ) { }

  ngOnInit(): void {
    this.loadForm();

    this.loadOptions();
  }

  private loadForm(): void {
    const fields = {
      name: new FormControl(this.model()?.name),
      releaseDate: new FormControl(this.model()?.releaseDate),
      duration: new FormControl(this.model()?.duration),
      content: new FormControl(this.model()?.content),
      thumbnail: new FormControl(this.model()?.thumbnail),
      genre: new FormControl(this.model()?.genres),
      studio: new FormControl(this.model()?.studios),
      talent: new FormControl(this.model()?.talents),
      language: new FormControl(this.model()?.language),
    };

    Object.entries(fields).forEach(([key, control]) => {
      this.form.addControl(key, control);
    });
  }

  private loadOptions(): void {
    this.genreService.getAll().subscribe((genres) => {
      this.genres.set(genres.map((genre) => ({
        label: genre.name,
        value: genre.id,
        checked: this.model()?.genres.includes(genre.name) || false
      })));
    });

    this.studioService.getAll().subscribe((studios) => {
      this.studios.set(studios.map((studio) => ({
        label: studio.name,
        value: studio.id,
        checked: this.model()?.studios.includes(studio.name) || false
      })));
    });

    this.talentService.getAll().subscribe((talents) => {
      this.talents.set(talents.map((talent) => ({
        label: talent.name,
        value: talent.id,
        checked: this.model()?.talents.includes(talent.name) || false
      })));
    });

    this.languageService.getAll().subscribe((languages) => {
      this.languages.set(languages.map((language) => ({
        label: language.name,
        value: language.id,
        selected: this.model()?.language === language.name || false
      })));
    });
  }
}
