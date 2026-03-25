import { Injectable } from '@angular/core';
import { EntityService } from '../entity.service';
import { InvoiceModel } from '../../model/invoice/invoice.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class InvoiceService extends EntityService<InvoiceModel> {
  protected override baseUrl: string = 'http://localhost:8080/api/invoices';

  override getAll(): Observable<InvoiceModel[]> {
    return this.http.get<InvoiceModel[]>(this.baseUrl);
  }

  override getById(id: string): Observable<InvoiceModel> {
    return this.http.get<InvoiceModel>(`${this.baseUrl}/${id}`);
  }

  override create(model: unknown): Observable<InvoiceModel> {
    return this.http.post<InvoiceModel>(`${this.baseUrl}/create`, model);
  }

  override update(id: string, model: unknown): Observable<InvoiceModel> {
    return this.http.put<InvoiceModel>(`${this.baseUrl}/${id}`, model);
  }

  override delete(id: string): Observable<null> {
    return this.http.delete<null>(`${this.baseUrl}/${id}`);
  }
}
