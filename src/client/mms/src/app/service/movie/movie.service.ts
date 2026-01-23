import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MovieModel } from '../../model/movie.model';
import { MovieSearchModel } from '../../model/search/movie-search.model';
import { PaginatedResult } from '../../shared/model/paginated-result.model';
import { MovieFormModel } from '../../model/form/movie-form.model';

@Injectable({
  providedIn: 'root',
})
export class MovieService {
  private readonly baseUrl = 'http://localhost:8080/api/movies';

  constructor(private readonly http: HttpClient) { }

  getAll(): Observable<MovieModel[]> {
    return this.http.get<MovieModel[]>(this.baseUrl);
  }

  getById(id: string): Observable<MovieModel> {
    return this.http.get<MovieModel>(`${this.baseUrl}/${id}`);
  }

  create(movie: MovieFormModel): Observable<MovieModel> {
    return this.http.post<MovieModel>(`${this.baseUrl}/create`, movie);
  }

  update(id: string, movie: MovieFormModel): Observable<MovieModel> {
    return this.http.put<MovieModel>(`${this.baseUrl}/${id}`, movie);
  }

  delete(id: string): Observable<null> {
    return this.http.delete<null>(`${this.baseUrl}/${id}`);
  }

  search(filter: MovieSearchModel): Observable<PaginatedResult<MovieModel>> {
    return this.http.post<PaginatedResult<MovieModel>>(`${this.baseUrl}/search`, filter );
  }
}
