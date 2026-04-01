import { Component } from '@angular/core';
import { Search } from '../../../shared/component/search/search';
import { Table } from '../../../shared/component/table/table';
import { PromotionFilter } from '../filter/promotion-filter';
import { PromotionCreateEdit } from '../create-edit/promotion-create-edit';
import { PromotionDetail } from '../detail/promotion-detail';
import { PromotionModel } from '../../../model/promotion/promotion.model';
import { PromotionService } from '../../../service/promotion/promotion.service';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { getRoleConfig } from '../../../shared/config/role-config';
import { SearchableFeature } from '../../../shared/component/feature/searchable-feature';
import { Pagination } from '../../../shared/component/pagination/pagination';

@Component({
  selector: 'app-promotion',
  imports: [Search, Table, ReactiveFormsModule, Pagination],
  templateUrl: './promotion.html',
  styleUrl: './promotion.css',
})
export class Promotion extends SearchableFeature<PromotionModel> {
  override entityName = 'Promotion';

  override contentCreateEdit = PromotionCreateEdit;
  override contentDetail = PromotionDetail;
  override contentFilter = PromotionFilter;

  override columns: TableColumnModel<PromotionModel>[] = [
    { key: 'title', label: 'Title', type: 'string' },
    { key: 'startDate', label: 'Start Date', type: 'date' },
    { key: 'endDate', label: 'End Date', type: 'date' },
    { key: 'discount', label: 'Discount (%)', type: 'percentage' },
  ];

  override sortOptions = [
    { label: 'Title', value: 'title' },
    { label: 'Start Date', value: 'startDate' },
    { label: 'End Date', value: 'endDate' },
    { label: 'Discount', value: 'discount' },
  ];

  override roleConfig = getRoleConfig(this.entityName);

  constructor(promotionService: PromotionService) {
    super(promotionService);
  }

  protected override getFilterGroup(): FormGroup {
    return this.formBuilder.nonNullable.group({
      startDate: [''],
      endDate: [''],
    });
  }
}
