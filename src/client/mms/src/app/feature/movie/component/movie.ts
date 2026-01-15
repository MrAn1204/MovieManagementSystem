import { Component, OnInit, signal } from '@angular/core';
import { Search } from "../../../shared/component/search/search";
import { Table } from "../../../shared/component/table/table";
import { MovieFilter } from "../filter/movie-filter";
import { MovieCreateEdit } from '../create-edit/movie-create-edit';
import { MovieDetail } from '../detail/movie-detail';
import { MovieModel } from '../../../model/movie.model';
import { MovieService } from '../../../service/movie/movie.service';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { TableColumnModel } from '../../../shared/model/table-column.model';

@Component({
  selector: 'app-movie',
  imports: [Search, Table, ReactiveFormsModule],
  templateUrl: './movie.html',
  styleUrl: './movie.css',
})
export class Movie implements OnInit {
  movieCreateEdit = MovieCreateEdit;
  movieDetail = MovieDetail;
  movieFilter = MovieFilter;

  columns: TableColumnModel[] = [
    { key: 'name', label: 'Name', type: 'string' },
    { key: 'releaseDate', label: 'Release Date', type: 'date' },
    { key: 'duration', label: 'Duration', type: 'number' },
    { key: 'genres', label: 'Genre', type: 'id-name-array' },
    { key: 'studios', label: 'Studio', type: 'id-name-array' },
    { key: 'language', label: 'Language', type: 'id-name' }
  ];
  movies = signal<MovieModel[]>([]);

  form: FormGroup = new FormGroup({});

  constructor(private readonly movieService: MovieService) {}
  
  ngOnInit(): void {
    this.movieService.getAll().subscribe(movies => {
      this.movies.set(movies);
    });
  }
  
}
