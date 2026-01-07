import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LanguageModel } from '../../model/language.model';

@Injectable({
  providedIn: 'root',
})
export class LanguageService {
  private readonly baseUrl = 'http://localhost:8080/api/languages';

  constructor(private readonly http: HttpClient) { }

  getAll(): Observable<LanguageModel[]> {
    return this.http.get<LanguageModel[]>(this.baseUrl);
  }

  getById(id: string): Observable<LanguageModel> {
    return this.http.get<LanguageModel>(`${this.baseUrl}/${id}`);
  }

  create(movie: LanguageModel): Observable<LanguageModel> {
    return this.http.post<LanguageModel>(this.baseUrl, movie);
  }

  update(id: string, movie: LanguageModel): Observable<LanguageModel> {
    return this.http.put<LanguageModel>(`${this.baseUrl}/${id}`, movie);
  }

  delete(id: string): Observable<null> {
    return this.http.delete<null>(`${this.baseUrl}/${id}`);
  }
}
