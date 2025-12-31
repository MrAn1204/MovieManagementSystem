import { Component } from '@angular/core';
import { Search } from "../../../shared/component/search/search";
import { Table } from "../../../shared/component/table/table";
import { MovieFilter } from "../filter/movie-filter";
import { MovieCreateEdit } from '../create-edit/movie-create-edit';

@Component({
  selector: 'app-movie',
  imports: [Search, Table, MovieFilter],
  templateUrl: './movie.html',
  styleUrl: './movie.css',
})
export class Movie {
  movieCreateEdit = MovieCreateEdit;

  columns: Map<string, string> = new Map([
    ['name', 'Name'],
    ['releaseDate', 'Release Date'],
    ['duration', 'Duration'],
    ['genre', 'Genre'],
    ['studio', 'Studio'],
    ['language', 'Language']
  ]);
  movies = [];

  constructor() {}
}
