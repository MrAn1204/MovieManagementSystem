import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MovieModel } from '../../model/movie/movie.model';
import { MovieSearchModel } from '../../model/search/movie-search.model';
import { PaginatedResult } from '../../shared/model/paginated-result.model';
import { MovieFormModel } from '../../model/form/movie-form.model';
import { FormMapper } from '../../shared/util/form-mapper';
import { EntityService } from '../entity.service';
import { MovieDetailModel } from '../../model/movie/movie-detail.model';

@Injectable({
  providedIn: 'root',
})
export class MovieService extends EntityService<MovieModel, MovieDetailModel> {
  protected override baseUrl: string = 'http://localhost:8080/api/movies';

  getAll(): Observable<MovieModel[]> {
    return this.http.get<MovieModel[]>(this.baseUrl);
  }

  getById(id: string): Observable<MovieDetailModel> {
    return this.http.get<MovieDetailModel>(`${this.baseUrl}/${id}`);
  }

  create(movie: MovieFormModel): Observable<MovieDetailModel> {
    return this.http.post<MovieDetailModel>(`${this.baseUrl}/create`, FormMapper.toFormData(movie));
  }

  update(id: string, movie: MovieFormModel): Observable<MovieDetailModel> {
    return this.http.put<MovieDetailModel>(`${this.baseUrl}/${id}`, FormMapper.toFormData(movie));
  }

  delete(id: string): Observable<null> {
    return this.http.delete<null>(`${this.baseUrl}/${id}`);
  }

  override search(filter: MovieSearchModel): Observable<PaginatedResult<MovieModel>> {
    return this.http.post<PaginatedResult<MovieModel>>(`${this.baseUrl}/search`, filter );
  }
}
