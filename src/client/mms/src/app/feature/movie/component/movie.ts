import { Component } from '@angular/core';
import { Search } from "../../../shared/component/search/search";
import { Table } from "../../../shared/component/table/table";
import { MovieFilter } from "../filter/movie-filter";
import { MovieCreateEdit } from '../create-edit/movie-create-edit';
import { MovieDetail } from '../detail/movie-detail';
import { MovieModel } from '../../../model/movie.model';
import { MovieService } from '../../../service/movie/movie.service';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { BaseFeature } from '../../../shared/component/feature/base-feature';

@Component({
  selector: 'app-movie',
  imports: [Search, Table, ReactiveFormsModule],
  templateUrl: './movie.html',
  styleUrl: './movie.css',
})
export class Movie extends BaseFeature<MovieModel> {
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

  constructor(private readonly movieService: MovieService) {
    super();
  }

  protected override getFilterGroup(): FormGroup {
    return this.formBuilder.group({
      languageId: [''],
      genreIds: [[]],
      studioIds: [[]],
      releaseAfter: [''],
      releaseBefore: [''],
    });
  }

  protected override getUpsertGroup(): FormGroup {
    return this.formBuilder.group({
      name: [''],
      releaseDate: [''],
      duration: [''],
      content: [''],
      thumbnail: [''],
      genres: [[]],
      studios: [[]],
      talents: [[]],
      language: [''],
    });
  }

  override onSearch(): void {
    this.movieService.search(this.searchForm.value).subscribe(res => {
      this.data.set(res);
    });
  }

  override saveNew(): void {
    console.log(this.entityForm.value);
  }

  override saveUpdate(): void {
    console.log(this.entityForm.value);
  }

  override onEdit(id: string): void {
    this.movieService.getById(id).subscribe(res => {
      this.entityForm.patchValue(res);
      this.displayEdit(res);
    });
  }

  override onView(id: string): void {
    this.movieService.getById(id).subscribe(res => {
      this.displayInfo(res);
    });
  }
}
