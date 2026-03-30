import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TicketModel } from '../../model/ticket/ticket.model';
import { TicketDetailModel } from '../../model/ticket/ticket-detail.model';
import { TicketFormModel } from '../../model/form/ticket-form.model';
import { TicketSearchModel } from '../../model/search/ticket-search.model';
import { PaginatedResult } from '../../shared/model/paginated-result.model';
import { EntityService } from '../entity.service';

@Injectable({
  providedIn: 'root',
})
export class TicketService extends EntityService<TicketModel> {
  protected override baseUrl = 'http://localhost:8080/api/tickets';

  getAll(): Observable<TicketModel[]> {
    return this.http.get<TicketModel[]>(this.baseUrl);
  }

  getById(id: string): Observable<TicketDetailModel> {
    return this.http.get<TicketDetailModel>(`${this.baseUrl}/${id}`);
  }

  create(ticket: TicketFormModel): Observable<TicketDetailModel> {
    const payload = {
      scheduleId: ticket.scheduleId,
      seatIds: ticket.seatIds,
      promotionId: ticket.promotionId || null,
      userId: ticket.userId,
    };

    return this.http.post<TicketDetailModel>(`${this.baseUrl}/create`, payload);
  }

  update(id: string, ticket: TicketFormModel): Observable<TicketDetailModel> {
    const payload = {
      scheduleId: ticket.scheduleId,
      seatId: ticket.seatIds[0],
      promotionId: ticket.promotionId || null,
    };

    return this.http.put<TicketDetailModel>(`${this.baseUrl}/${id}`, payload);
  }

  delete(id: string): Observable<null> {
    return this.http.delete<null>(`${this.baseUrl}/${id}`);
  }

  override search(filter: TicketSearchModel): Observable<PaginatedResult<TicketModel>> {
    return this.http.post<PaginatedResult<TicketModel>>(`${this.baseUrl}/search`, filter);
  }
}
