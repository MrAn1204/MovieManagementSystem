import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TalentModel } from '../../model/movie/talent.model';

@Injectable({
  providedIn: 'root',
})
export class TalentService {
  private readonly baseUrl = 'http://localhost:8080/api/talents';

  constructor(private readonly http: HttpClient) { }

  getAll(): Observable<TalentModel[]> {
    return this.http.get<TalentModel[]>(this.baseUrl);
  }

  getById(id: string): Observable<TalentModel> {
    return this.http.get<TalentModel>(`${this.baseUrl}/${id}`);
  }

  create(movie: TalentModel): Observable<TalentModel> {
    return this.http.post<TalentModel>(this.baseUrl, movie);
  }

  update(id: string, movie: TalentModel): Observable<TalentModel> {
    return this.http.put<TalentModel>(`${this.baseUrl}/${id}`, movie);
  }

  delete(id: string): Observable<null> {
    return this.http.delete<null>(`${this.baseUrl}/${id}`);
  }
}
