import { Component, signal } from '@angular/core';
import { Search } from "../../../shared/component/search/search";
import { Table } from "../../../shared/component/table/table";
import { MovieFilter } from "../filter/movie-filter";
import { MovieCreateEdit } from '../create-edit/movie-create-edit';
import { MovieDetail } from '../detail/movie-detail';
import { MovieModel } from '../../../model/movie.model';
import { MovieService } from '../../../service/movie/movie.service';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { MovieSearchModel } from '../../../model/search/movie-search.model';
import { createEmptyPaginatedResult, PaginatedResult } from '../../../shared/model/paginated-result.model';

@Component({
  selector: 'app-movie',
  imports: [Search, Table, ReactiveFormsModule],
  templateUrl: './movie.html',
  styleUrl: './movie.css',
})
export class Movie {
  movieCreateEdit = MovieCreateEdit;
  movieDetail = MovieDetail;
  movieFilter = MovieFilter;

  pageNumber = signal<number>(1);
  pageSize = 10;
  pageCount = 10;

  searchForm = new FormGroup({
    languageId: new FormControl(''),
    genreIds: new FormControl([]),
    studioIds: new FormControl([]),
    releaseAfter: new FormControl(''),
    releaseBefore: new FormControl(''),
  });

  columns: TableColumnModel[] = [
    { key: 'name', label: 'Name', type: 'string' },
    { key: 'releaseDate', label: 'Release Date', type: 'date' },
    { key: 'duration', label: 'Duration', type: 'number' },
    { key: 'genres', label: 'Genre', type: 'id-name-array' },
    { key: 'studios', label: 'Studio', type: 'id-name-array' },
    { key: 'language', label: 'Language', type: 'id-name' }
  ];
  movies = signal<PaginatedResult<MovieModel>>(createEmptyPaginatedResult<MovieModel>());

  constructor(private readonly movieService: MovieService) { }

  onSearch(formData: MovieSearchModel): void {
    this.movieService.search(formData).subscribe(res => {
      this.movies.set(res);
    });
  }

  onChangePage(page: number): void {
    this.pageNumber.set(page);
  }
}
