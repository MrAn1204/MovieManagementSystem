import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { StatisticsSummaryModel } from '../../model/statistics/statistics-summary.model';

@Injectable({
  providedIn: 'root',
})
export class StatisticsService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api/statistics';

  getSummary(): Observable<StatisticsSummaryModel> {
    return this.http.get<StatisticsSummaryModel>(`${this.baseUrl}/summary`);
  }
}
