import { Component, OnInit, signal } from '@angular/core';
import { Search } from "../../../shared/component/search/search";
import { Table } from "../../../shared/component/table/table";
import { MovieFilter } from "../filter/movie-filter";
import { MovieCreateEdit } from '../create-edit/movie-create-edit';
import { MovieDetail } from '../detail/movie-detail';
import { MovieModel } from '../../../model/movie.model';
import { MovieService } from '../../../service/movie/movie.service';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

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

  columns: Map<string, string> = new Map([
    ['name', 'Name'],
    ['releaseDate', 'Release Date'],
    ['duration', 'Duration'],
    ['genres', 'Genre'],
    ['studios', 'Studio'],
    ['language', 'Language']
  ]);
  movies = signal<MovieModel[]>([]);

  form: FormGroup = new FormGroup({});

  constructor(private readonly movieService: MovieService) {}
  
  ngOnInit(): void {
    this.movieService.getAll().subscribe(movies => {
      this.movies.set(movies);
    });
  }
  
}
