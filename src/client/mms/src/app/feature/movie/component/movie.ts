import { Component } from '@angular/core';
import { Search } from "../../../shared/component/search/search";
import { Table } from "../../../shared/component/table/table";
import { MovieFilter } from "../filter/movie-filter";
import { MovieCreateEdit } from '../create-edit/movie-create-edit';
import { MovieDetail } from '../detail/movie-detail';
import { MovieModel } from '../../../model/movie.model';
import { MovieService } from '../../../service/movie/movie.service';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { AppFeature } from '../../../shared/component/feature/base-feature';

@Component({
  selector: 'app-movie',
  imports: [Search, Table, ReactiveFormsModule],
  templateUrl: './movie.html',
  styleUrl: './movie.css',
})
export class Movie extends AppFeature<MovieModel> {
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
    this.searchForm.addControl('languageId', new FormControl(''));
    this.searchForm.addControl('genreIds', new FormControl([]));
    this.searchForm.addControl('studioIds', new FormControl([]));
    this.searchForm.addControl('releaseAfter', new FormControl(''));
    this.searchForm.addControl('releaseBefore', new FormControl(''));
  }

  override onSearch(): void {
    this.movieService.search(this.searchForm.value).subscribe(res => {
      this.data.set(res);
    });
  }
}
