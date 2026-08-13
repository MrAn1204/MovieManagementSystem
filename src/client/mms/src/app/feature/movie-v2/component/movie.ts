import { Component, inject } from '@angular/core';
import { MovieFilterV2 } from "../filter/movie-filter";
import { MovieModel } from '../../../model/movie/movie.model';
import { MovieService } from '../../../service/movie/movie.service';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { getRoleConfig } from '../../../shared/config/role-config';
import { TableV2 } from "../../../shared/component-v2/table/table";
import { Paginator } from "../../../shared/component-v2/paginator/paginator";
import { MovieAddEdit } from '../add-edit/movie-add-edit';
import { MovieDetailV2 } from '../detail/movie-detail';
import { SearchV2 } from "../../../shared/component-v2/search/search";
import { SearchableFeatureV2 } from '../../../shared/component-v2/feature/searchable-feature';
import { EntityService } from '../../../service/entity.service';
import { MovieDialogService } from '../../../service/dialog-v2/movie/movie-dialog.service';
import { EntityDialogServiceV2 } from '../../../service/dialog-v2/entity-dialog.service';

@Component({
  selector: 'app-movie',
  imports: [ReactiveFormsModule, TableV2, Paginator, SearchV2, MovieFilterV2],
  templateUrl: './movie.html',
  styleUrl: './movie.css',
})
export class MovieV2 extends SearchableFeatureV2<MovieModel> {
  override entityName = "Movie";

  override contentAddEdit = MovieAddEdit;
  override contentDetail = MovieDetailV2;

  override sortOptions = [
    { label: 'Name', value: 'name' },
    { label: 'Release Date', value: 'releaseDate' },
    { label: 'Duration', value: 'duration' },
  ]

  override filterForm: FormGroup = this.formBuilder.nonNullable.group({
    languageId: [''],
    genreIds: [[]],
    studioIds: [[]],
    releaseAfter: [''],
    releaseBefore: [''],
  });

  override columns: TableColumnModel<MovieModel>[] = [
    { key: 'name', label: 'Name', type: 'string' },
    { key: 'releaseDate', label: 'Release Date', type: 'date' },
    { key: 'duration', label: 'Duration', type: 'number' },
    { key: 'genres', label: 'Genre', type: 'id-name-array' },
    { key: 'studios', label: 'Studio', type: 'id-name-array' },
    { key: 'language', label: 'Language', type: 'id-name' }
  ];

  override roleConfig = getRoleConfig(this.entityName);

  protected override entityService: EntityService<MovieModel> = inject(MovieService);
  protected override dialogService: EntityDialogServiceV2<MovieModel> = inject(MovieDialogService);

}
