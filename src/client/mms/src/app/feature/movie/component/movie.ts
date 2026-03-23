import { Component } from '@angular/core';
import { Search } from "../../../shared/component/search/search";
import { Table } from "../../../shared/component/table/table";
import { MovieFilter } from "../filter/movie-filter";
import { MovieCreateEdit } from '../create-edit/movie-create-edit';
import { MovieDetail } from '../detail/movie-detail';
import { MovieModel } from '../../../model/movie/movie.model';
import { MovieService } from '../../../service/movie/movie.service';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { getRoleConfig } from '../../../shared/config/role-config';
import { SearchableFeature } from '../../../shared/component/feature/searchable-feature';
import { CustomValidators } from '../../../shared/util/custom-validators';

@Component({
  selector: 'app-movie',
  imports: [Search, Table, ReactiveFormsModule],
  templateUrl: './movie.html',
  styleUrl: './movie.css',
})
export class Movie extends SearchableFeature<MovieModel> {
  override entityName = "Movie";

  override contentCreateEdit = MovieCreateEdit;
  override contentDetail = MovieDetail;
  override contentFilter = MovieFilter;

  override columns: TableColumnModel<MovieModel>[] = [
    { key: 'name', label: 'Name', type: 'string' },
    { key: 'releaseDate', label: 'Release Date', type: 'date' },
    { key: 'duration', label: 'Duration', type: 'number' },
    { key: 'genres', label: 'Genre', type: 'id-name-array' },
    { key: 'studios', label: 'Studio', type: 'id-name-array' },
    { key: 'language', label: 'Language', type: 'id-name' }
  ];

  override sortOptions = [
    { label: 'Name', value: 'name' },
    { label: 'Release Date', value: 'releaseDate' },
    { label: 'Duration', value: 'duration' },
  ];

  override roleConfig = getRoleConfig(this.entityName);

  constructor(movieService: MovieService) {
    super(movieService);
  }

  protected override getFilterGroup(): FormGroup {
    return this.formBuilder.nonNullable.group({
      languageId: [''],
      genreIds: [[]],
      studioIds: [[]],
      releaseAfter: [''],
      releaseBefore: [''],
    });
  }

  protected override getUpsertGroup(): FormGroup {
    return this.formBuilder.nonNullable.group({
      name: ['', [CustomValidators.required('movie.name.required')]],
      releaseDate: [''],
      duration: [0, [CustomValidators.min(1, 'movie.duration.invalid')]],
      content: [''],
      thumbnail: [null],
      genreIds: [[]],
      studioIds: [[]],
      talentIds: [[]],
      languageId: [''],
    });
  }

  override patchEntityForm(model: MovieModel): void {
    this.entityForm.patchValue({
      ...model,
      genreIds: model.genres?.map(genre => genre.id) ?? [],
      studioIds: model.studios?.map(studio => studio.id) ?? [],
      talentIds: model.talents?.map(talent => talent.id) ?? [],
      languageId: model.language?.id ?? null,
    });
  }
}
