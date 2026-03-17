import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ScheduleModel } from '../../model/schedule/schedule.model';
import { ScheduleSearchModel } from '../../model/search/schedule-search.model';
import { PaginatedResult } from '../../shared/model/paginated-result.model';
import { ScheduleFormModel } from '../../model/form/schedule-form.model';
import { ScheduleDetailModel } from '../../model/schedule/schedule-detail.model';
import { EntityService } from '../entity.service';

@Injectable({
  providedIn: 'root',
})
export class ScheduleService extends EntityService<ScheduleModel> {
  protected override baseUrl = 'http://localhost:8080/api/schedules';

  getAll(): Observable<ScheduleModel[]> {
    return this.http.get<ScheduleModel[]>(this.baseUrl);
  }

  getById(id: string): Observable<ScheduleDetailModel> {
    return this.http.get<ScheduleDetailModel>(`${this.baseUrl}/${id}`);
  }

  create(schedule: ScheduleFormModel): Observable<ScheduleDetailModel> {
    return this.http.post<ScheduleDetailModel>(`${this.baseUrl}/create`, schedule);
  }

  update(id: string, schedule: ScheduleFormModel): Observable<ScheduleDetailModel> {
    return this.http.put<ScheduleDetailModel>(`${this.baseUrl}/${id}`, schedule);
  }

  delete(id: string): Observable<null> {
    return this.http.delete<null>(`${this.baseUrl}/${id}`);
  }

  override search(filter: ScheduleSearchModel): Observable<PaginatedResult<ScheduleModel>> {
    return this.http.post<PaginatedResult<ScheduleModel>>(`${this.baseUrl}/search`, filter);
  }
}
