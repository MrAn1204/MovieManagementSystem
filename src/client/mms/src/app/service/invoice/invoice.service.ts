import { Injectable } from '@angular/core';
import { EntityService } from '../entity.service';
import { InvoiceModel } from '../../model/invoice/invoice.model';
import { Observable } from 'rxjs';
import { InvoiceDetailModel } from '../../model/invoice/invoice-detail.model';

@Injectable({
  providedIn: 'root',
})
export class InvoiceService extends EntityService<InvoiceModel, InvoiceDetailModel> {
  protected override baseUrl: string = 'http://localhost:8080/api/invoices';

  override getAll(): Observable<InvoiceModel[]> {
    return this.http.get<InvoiceModel[]>(this.baseUrl);
  }

  override getById(id: string): Observable<InvoiceDetailModel> {
    return this.http.get<InvoiceDetailModel>(`${this.baseUrl}/${id}`);
  }

  override create(model: unknown): Observable<InvoiceDetailModel> {
    return this.http.post<InvoiceDetailModel>(`${this.baseUrl}/create`, model);
  }

  override update(id: string, model: unknown): Observable<InvoiceDetailModel> {
    return this.http.put<InvoiceDetailModel>(`${this.baseUrl}/${id}`, model);
  }

  override delete(id: string): Observable<null> {
    return this.http.delete<null>(`${this.baseUrl}/${id}`);
  }
}
