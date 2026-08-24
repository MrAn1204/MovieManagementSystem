import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SeatModel } from '../../model/seat/seat.model';
import { SeatFormModel } from '../../model/form/seat-form.model';
import { SeatTypeModel } from '../../model/seat/seat-type.model';
import { EntityService } from '../entity.service';
import { SeatDetailModel } from '../../model/seat/seat-detail.model';

@Injectable({
  providedIn: 'root',
})
export class SeatService extends EntityService<SeatModel, SeatDetailModel> {
  protected override baseUrl = 'http://localhost:8080/api/seats';

  getAll(): Observable<SeatModel[]> {
    return this.http.get<SeatModel[]>(this.baseUrl);
  }

  getById(id: string): Observable<SeatDetailModel> {
    return this.http.get<SeatDetailModel>(`${this.baseUrl}/${id}`);
  }

  create(seat: SeatFormModel): Observable<SeatDetailModel> {
    return this.http.post<SeatDetailModel>(`${this.baseUrl}/create`, seat);
  }

  update(id: string, seat: SeatFormModel): Observable<SeatDetailModel> {
    return this.http.put<SeatDetailModel>(`${this.baseUrl}/${id}`, seat);
  }

  delete(id: string): Observable<null> {
    return this.http.delete<null>(`${this.baseUrl}/${id}`);
  }

  getSeatTypes(): Observable<SeatTypeModel> {
    return this.http.get<SeatTypeModel>(`${this.baseUrl}/seat-types`);
  }
}
