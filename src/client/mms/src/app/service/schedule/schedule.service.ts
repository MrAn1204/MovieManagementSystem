import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ScheduleModel } from '../../model/schedule.model';
import { ScheduleSearchModel } from '../../model/search/schedule-search.model';
import { PaginatedResult } from '../../shared/model/paginated-result.model';
import { ScheduleFormModel } from '../../model/form/schedule-form.model';

@Injectable({
  providedIn: 'root',
})
export class ScheduleService {
  private readonly baseUrl = 'http://localhost:8080/api/schedules';

  constructor(private readonly http: HttpClient) { }

  getAll(): Observable<ScheduleModel[]> {
    return this.http.get<ScheduleModel[]>(this.baseUrl);
  }

  getById(id: string): Observable<ScheduleModel> {
    return this.http.get<ScheduleModel>(`${this.baseUrl}/${id}`);
  }

  create(schedule: ScheduleFormModel): Observable<ScheduleModel> {
    return this.http.post<ScheduleModel>(`${this.baseUrl}/create`, schedule);
  }

  update(id: string, schedule: ScheduleFormModel): Observable<ScheduleModel> {
    return this.http.put<ScheduleModel>(`${this.baseUrl}/${id}`, schedule);
  }

  delete(id: string): Observable<null> {
    return this.http.delete<null>(`${this.baseUrl}/${id}`);
  }

  search(filter: ScheduleSearchModel): Observable<PaginatedResult<ScheduleModel>> {
    return this.http.post<PaginatedResult<ScheduleModel>>(`${this.baseUrl}/search`, filter);
  }
}
