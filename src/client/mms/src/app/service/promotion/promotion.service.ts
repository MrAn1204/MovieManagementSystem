import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PromotionModel } from '../../model/promotion/promotion.model';
import { PromotionSearchModel } from '../../model/search/promotion-search.model';
import { PaginatedResult } from '../../shared/model/paginated-result.model';
import { PromotionFormModel } from '../../model/form/promotion-form.model';
import { EntityService } from '../entity.service';
import { FormMapper } from '../../shared/util/form-mapper';

@Injectable({
  providedIn: 'root',
})
export class PromotionService extends EntityService<PromotionModel> {
  protected override baseUrl = 'http://localhost:8080/api/promotions';

  getAll(): Observable<PromotionModel[]> {
    return this.http.get<PromotionModel[]>(this.baseUrl);
  }

  getById(id: string): Observable<PromotionModel> {
    return this.http.get<PromotionModel>(`${this.baseUrl}/${id}`);
  }

  create(promotion: PromotionFormModel): Observable<PromotionModel> {
    return this.http.post<PromotionModel>(`${this.baseUrl}/create`, FormMapper.toFormData(promotion));
  }

  update(id: string, promotion: PromotionFormModel): Observable<PromotionModel> {
    return this.http.put<PromotionModel>(`${this.baseUrl}/${id}`, FormMapper.toFormData(promotion));
  }

  delete(id: string): Observable<null> {
    return this.http.delete<null>(`${this.baseUrl}/${id}`);
  }

  override search(filter: PromotionSearchModel): Observable<PaginatedResult<PromotionModel>> {
    return this.http.post<PaginatedResult<PromotionModel>>(`${this.baseUrl}/search`, filter);
  }
}
