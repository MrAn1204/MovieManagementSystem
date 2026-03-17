import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StudioModel } from '../../model/movie/studio.model';
import { EntityService } from '../entity.service';

@Injectable({
  providedIn: 'root',
})
export class StudioService extends EntityService<StudioModel> {
  protected override baseUrl = 'http://localhost:8080/api/studios';

  getAll(): Observable<StudioModel[]> {
    return this.http.get<StudioModel[]>(this.baseUrl);
  }

  getById(id: string): Observable<StudioModel> {
    return this.http.get<StudioModel>(`${this.baseUrl}/${id}`);
  }

  create(movie: StudioModel): Observable<StudioModel> {
    return this.http.post<StudioModel>(this.baseUrl, movie);
  }

  update(id: string, movie: StudioModel): Observable<StudioModel> {
    return this.http.put<StudioModel>(`${this.baseUrl}/${id}`, movie);
  }

  delete(id: string): Observable<null> {
    return this.http.delete<null>(`${this.baseUrl}/${id}`);
  }
}
