import { Component } from '@angular/core';
import { Search } from "../../../shared/component/search/search";
import { Table } from "../../../shared/component/table/table";

@Component({
  selector: 'app-movie',
  imports: [Search, Table],
  templateUrl: './movie.html',
  styleUrl: './movie.css',
})
export class Movie {
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
