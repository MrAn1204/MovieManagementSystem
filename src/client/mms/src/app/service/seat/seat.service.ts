import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SeatModel } from '../../model/seat.model';
import { SeatFormModel } from '../../model/form/seat-form.model';
import { FormMapper } from '../../shared/util/form-mapper';

@Injectable({
  providedIn: 'root',
})
export class SeatService {
    private readonly baseUrl = 'http://localhost:8080/api/seats';

  constructor(private readonly http: HttpClient) { }

  getAll(): Observable<SeatModel[]> {
    return this.http.get<SeatModel[]>(this.baseUrl);
  }

  getById(id: string): Observable<SeatModel> {
    return this.http.get<SeatModel>(`${this.baseUrl}/${id}`);
  }

  create(seat: SeatFormModel): Observable<SeatModel> {
    return this.http.post<SeatModel>(`${this.baseUrl}/create`, FormMapper.toFormData(seat));
  }

  update(id: string, seat: SeatFormModel): Observable<SeatModel> {
    return this.http.put<SeatModel>(`${this.baseUrl}/${id}`, FormMapper.toFormData(seat));
  }

  delete(id: string): Observable<null> {
    return this.http.delete<null>(`${this.baseUrl}/${id}`);
  }

  getAllInRoom(roomId: string): Observable<SeatModel[]> {
    return this.http.post<SeatModel[]>(`${this.baseUrl}/seat-map`, { roomId: roomId });
  }
}
