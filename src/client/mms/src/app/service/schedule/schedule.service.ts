import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ScheduleModel } from '../../model/schedule/schedule.model';
import { ScheduleSearchModel } from '../../model/search/schedule-search.model';
import { PaginatedResult } from '../../shared/model/paginated-result.model';
import { ScheduleFormModel } from '../../model/form/schedule-form.model';
import { ScheduleDetailModel } from '../../model/schedule/schedule-detail.model';

@Injectable({
  providedIn: 'root',
})
export class ScheduleService {
  private readonly baseUrl = 'http://localhost:8080/api/schedules';

  constructor(private readonly http: HttpClient) { }

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

  search(filter: ScheduleSearchModel): Observable<PaginatedResult<ScheduleModel>> {
    return this.http.post<PaginatedResult<ScheduleModel>>(`${this.baseUrl}/search`, filter);
  }
}
