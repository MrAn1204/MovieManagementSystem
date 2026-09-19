import { Component, inject, signal } from '@angular/core';
import { AddEditDialog } from '../../../shared/component-v2/dialog/add-edit-dialog/add-edit-dialog';
import { MovieDetailModel } from '../../../model/movie/movie-detail.model';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { GenreService } from '../../../service/genre/genre.service';
import { StudioService } from '../../../service/studio/studio.service';
import { TalentService } from '../../../service/talent/talent.service';
import { LanguageService } from '../../../service/language/language.service';
import { AddEditContainer } from "../../../shared/component-v2/dialog/add-edit-container/add-edit-container";
import { ImageField } from "../../../shared/component/form/image/image-field";
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { FormSelect } from "../../../shared/component-v2/form/form-select/form-select";
import { FormTextArea } from "../../../shared/component-v2/form/form-textarea/form-textarea";
import { DetailEntityService } from '../../../service/detail-entity.service';
import { MovieService } from '../../../service/movie/movie.service';
import { MatError } from '@angular/material/select';

@Component({
  selector: 'app-movie-add-edit',
  imports: [AddEditContainer, ImageField, FormInput, FormSelect, FormTextArea, MatError],
  templateUrl: './movie-add-edit.html',
  styleUrl: './movie-add-edit.css',
})
export class MovieAddEdit extends AddEditDialog<MovieDetailModel> {
  protected override entityService: DetailEntityService<MovieDetailModel> = inject(MovieService);

  override form = this.formBuilder.nonNullable.group({
    name: ['', [CustomValidators.required('movie.name.required')]],
    releaseDate: [''],
    duration: [0, [CustomValidators.min(1, 'movie.duration.invalid')]],
    content: [''],
    thumbnail: [''],
    genreIds: [[]],
    studioIds: [[]],
    talentIds: [[]],
    languageId: [null],
  });

  genres = signal<FormOptionModel[]>([]);
  studios = signal<FormOptionModel[]>([]);
  talents = signal<FormOptionModel[]>([]);
  languages = signal<FormOptionModel[]>([]);

  constructor(
    private readonly genreService: GenreService,
    private readonly studioService: StudioService,
    private readonly talentService: TalentService,
    private readonly languageService: LanguageService,
  ) {
    super();
  }

  protected override mapForm(model: MovieDetailModel): void {
    this.onReset({
      name: model.name,
      releaseDate: model.releaseDate,
      duration: model.duration,
      content: model.content,
      thumbnail: model.thumbnail,
      genreIds: model.genres?.map(genre => genre.id) ?? [],
      studioIds: model.studios?.map(studio => studio.id) ?? [],
      talentIds: model.talents?.map(talent => talent.id) ?? [],
      languageId: model.language?.id ?? null,
    });
  }

  override loadOptions(): void {
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

    this.talentService.getAll().subscribe((talents) => {
      this.talents.set(talents.map((talent) => ({
        label: talent.name,
        value: talent.id,
      })));
    });

    this.languageService.getAll().subscribe((languages) => {
      this.languages.set(languages.map((language) => ({
        label: language.name,
        value: language.id,
      })));
    });
  }
}
