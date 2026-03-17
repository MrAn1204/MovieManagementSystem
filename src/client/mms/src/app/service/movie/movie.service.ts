import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MovieModel } from '../../model/movie/movie.model';
import { MovieSearchModel } from '../../model/search/movie-search.model';
import { PaginatedResult } from '../../shared/model/paginated-result.model';
import { MovieFormModel } from '../../model/form/movie-form.model';
import { FormMapper } from '../../shared/util/form-mapper';
import { EntityService } from '../entity.service';

@Injectable({
  providedIn: 'root',
})
export class MovieService extends EntityService<MovieModel> {
  protected override baseUrl: string = 'http://localhost:8080/api/movies';

  getAll(): Observable<MovieModel[]> {
    return this.http.get<MovieModel[]>(this.baseUrl);
  }

  getById(id: string): Observable<MovieModel> {
    return this.http.get<MovieModel>(`${this.baseUrl}/${id}`);
  }

  create(movie: MovieFormModel): Observable<MovieModel> {
    return this.http.post<MovieModel>(`${this.baseUrl}/create`, FormMapper.toFormData(movie));
  }

  update(id: string, movie: MovieFormModel): Observable<MovieModel> {
    return this.http.put<MovieModel>(`${this.baseUrl}/${id}`, FormMapper.toFormData(movie));
  }

  delete(id: string): Observable<null> {
    return this.http.delete<null>(`${this.baseUrl}/${id}`);
  }

  override search(filter: MovieSearchModel): Observable<PaginatedResult<MovieModel>> {
    return this.http.post<PaginatedResult<MovieModel>>(`${this.baseUrl}/search`, filter );
  }
}
