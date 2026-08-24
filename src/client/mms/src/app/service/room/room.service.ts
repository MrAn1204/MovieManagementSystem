import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RoomModel } from '../../model/room/room.model';
import { RoomSearchModel } from '../../model/search/room-search.model';
import { PaginatedResult } from '../../shared/model/paginated-result.model';
import { RoomFormModel } from '../../model/form/room-form.model';
import { RoomDetailModel } from '../../model/room/room-detail.model';
import { EntityService } from '../entity.service';

@Injectable({
  providedIn: 'root',
})
export class RoomService extends EntityService<RoomModel, RoomDetailModel> {
  protected override baseUrl = 'http://localhost:8080/api/rooms';

  getAll(): Observable<RoomModel[]> {
    return this.http.get<RoomModel[]>(this.baseUrl);
  }

  getById(id: string): Observable<RoomDetailModel> {
    return this.http.get<RoomDetailModel>(`${this.baseUrl}/${id}`);
  }

  create(room: RoomFormModel): Observable<RoomDetailModel> {
    return this.http.post<RoomDetailModel>(`${this.baseUrl}/create`, room);
  }

  update(id: string, room: RoomFormModel): Observable<RoomDetailModel> {
    return this.http.put<RoomDetailModel>(`${this.baseUrl}/${id}`, room);
  }

  delete(id: string): Observable<null> {
    return this.http.delete<null>(`${this.baseUrl}/${id}`);
  }

  override search(filter: RoomSearchModel): Observable<PaginatedResult<RoomModel>> {
    return this.http.post<PaginatedResult<RoomModel>>(`${this.baseUrl}/search`, filter);
  }
}
