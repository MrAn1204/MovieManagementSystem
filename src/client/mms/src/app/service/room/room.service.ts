import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RoomModel } from '../../model/room.model';
import { RoomSearchModel } from '../../model/search/room-search.model';
import { PaginatedResult } from '../../shared/model/paginated-result.model';
import { RoomFormModel } from '../../model/form/room-form.model';

@Injectable({
  providedIn: 'root',
})
export class RoomService {
  private readonly baseUrl = 'http://localhost:8080/api/rooms';

  constructor(private readonly http: HttpClient) { }

  getAll(): Observable<RoomModel[]> {
    return this.http.get<RoomModel[]>(this.baseUrl);
  }

  getById(id: string): Observable<RoomModel> {
    return this.http.get<RoomModel>(`${this.baseUrl}/${id}`);
  }

  create(room: RoomFormModel): Observable<RoomModel> {
    return this.http.post<RoomModel>(`${this.baseUrl}/create`, room);
  }

  update(id: string, room: RoomFormModel): Observable<RoomModel> {
    return this.http.put<RoomModel>(`${this.baseUrl}/${id}`, room);
  }

  delete(id: string): Observable<null> {
    return this.http.delete<null>(`${this.baseUrl}/${id}`);
  }

  search(filter: RoomSearchModel): Observable<PaginatedResult<RoomModel>> {
    return this.http.post<PaginatedResult<RoomModel>>(`${this.baseUrl}/search`, filter);
  }
}
