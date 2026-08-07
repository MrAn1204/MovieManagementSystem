import { Component, OnInit, signal } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { GenreService } from '../../../service/genre/genre.service';
import { StudioService } from '../../../service/studio/studio.service';
import { LanguageService } from '../../../service/language/language.service';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { FormSelect } from "../../../shared/component-v2/form/form-select/form-select";
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { Filter } from '../../../shared/component-v2/filter/filter';

@Component({
  selector: 'app-movie-filter',
  imports: [ReactiveFormsModule, FormSelect, FormInput],
  templateUrl: './movie-filter.html',
  styleUrl: './movie-filter.css',
})
export class MovieFilterV2 extends Filter<MovieFilterForm> implements OnInit {
  genres = signal<FormOptionModel[]>([]);
  studios = signal<FormOptionModel[]>([]);
  languages = signal<FormOptionModel[]>([]);

  constructor(
    private readonly genreService: GenreService,
    private readonly studioService: StudioService,
    private readonly languageService: LanguageService,
  ) {
    super();
  }

  ngOnInit(): void {
    this.genreService.getAll().subscribe((genres) => {
      this.genres.set(genres.map((genre) => ({
        label: genre.name,
        value: genre.id,
      })));
    });

    this.studioService.getAll().subscribe((studios) => {
      this.studios.set(studios.map((studio) => ({
        label: studio.name,
        value: studio.id,
      })));
    });

    this.languageService.getAll().subscribe((languages) => {
      const languageOptions: FormOptionModel[] = languages.map((language) => ({
        label: language.name,
        value: language.id,
      }));

      languageOptions.unshift({
        label: 'Any Language',
        value: '',
      });

      this.languages.set(languageOptions);
    });
  }
}

export type MovieFilterForm = {
  languageId: FormControl<string | undefined>;
  genreIds: FormControl<string[] | undefined>;
  studioIds: FormControl<string[] | undefined>;
  releaseAfter: FormControl<string | undefined>;
  releaseBefore: FormControl<string | undefined>;
}
