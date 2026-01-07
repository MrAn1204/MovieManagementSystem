import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GenreModel } from '../../model/genre.model';

@Injectable({
  providedIn: 'root',
})
export class GenreService {
  private readonly baseUrl = 'http://localhost:8080/api/genres';

  constructor(private readonly http: HttpClient) { }

  getAll(): Observable<GenreModel[]> {
    return this.http.get<GenreModel[]>(this.baseUrl);
  }

  getById(id: string): Observable<GenreModel> {
    return this.http.get<GenreModel>(`${this.baseUrl}/${id}`);
  }

  create(movie: GenreModel): Observable<GenreModel> {
    return this.http.post<GenreModel>(this.baseUrl, movie);
  }

  update(id: string, movie: GenreModel): Observable<GenreModel> {
    return this.http.put<GenreModel>(`${this.baseUrl}/${id}`, movie);
  }

  delete(id: string): Observable<null> {
    return this.http.delete<null>(`${this.baseUrl}/${id}`);
  }
}
