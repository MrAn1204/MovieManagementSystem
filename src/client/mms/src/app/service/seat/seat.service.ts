import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SeatModel } from '../../model/seat/seat.model';
import { SeatFormModel } from '../../model/form/seat-form.model';
import { SeatTypeModel } from '../../model/seat/seat-type.model';
import { EntityService } from '../entity.service';

@Injectable({
  providedIn: 'root',
})
export class SeatService extends EntityService<SeatModel> {
  protected override baseUrl = 'http://localhost:8080/api/seats';

  getAll(): Observable<SeatModel[]> {
    return this.http.get<SeatModel[]>(this.baseUrl);
  }

  getById(id: string): Observable<SeatModel> {
    return this.http.get<SeatModel>(`${this.baseUrl}/${id}`);
  }

  create(seat: SeatFormModel): Observable<SeatModel> {
    return this.http.post<SeatModel>(`${this.baseUrl}/create`, seat);
  }

  update(id: string, seat: SeatFormModel): Observable<SeatModel> {
    return this.http.put<SeatModel>(`${this.baseUrl}/${id}`, seat);
  }

  delete(id: string): Observable<null> {
    return this.http.delete<null>(`${this.baseUrl}/${id}`);
  }

  getSeatTypes(): Observable<SeatTypeModel> {
    return this.http.get<SeatTypeModel>(`${this.baseUrl}/seat-types`);
  }
}
