import { Component, inject } from '@angular/core';
import { PromotionFilterV2 } from "../filter/promotion-filter-v2";
import { PromotionModel } from '../../../model/promotion/promotion.model';
import { PromotionService } from '../../../service/promotion/promotion.service';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { getRoleConfig } from '../../../shared/config/role-config';
import { TableV2 } from "../../../shared/component-v2/table/table";
import { Paginator } from "../../../shared/component-v2/paginator/paginator";
import { PromotionAddEdit } from '../add-edit/promotion-add-edit';
import { PromotionDetailV2 } from '../detail/promotion-detail-v2';
import { SearchV2 } from "../../../shared/component-v2/search/search";
import { SearchableFeatureV2 } from '../../../shared/component-v2/feature/searchable-feature';
import { EntityService } from '../../../service/entity.service';
import { PromotionDialogService } from '../../../service/dialog-v2/promotion/promotion-dialog.service';
import { EntityDialogServiceV2 } from '../../../service/dialog-v2/entity-dialog.service';

@Component({
  selector: 'app-promotion-v2',
  imports: [ReactiveFormsModule, TableV2, Paginator, SearchV2, PromotionFilterV2],
  templateUrl: './promotion-v2.html',
  styleUrl: './promotion-v2.css',
})
export class PromotionV2 extends SearchableFeatureV2<PromotionModel> {
  override entityName = "Promotion";

  override contentAddEdit = PromotionAddEdit;
  override contentDetail = PromotionDetailV2;

  override sortOptions = [
    { label: 'Title', value: 'title' },
    { label: 'Start Date', value: 'startDate' },
    { label: 'End Date', value: 'endDate' },
    { label: 'Discount', value: 'discount' },
  ]

  override filterForm: FormGroup = this.formBuilder.nonNullable.group({
    startDate: [''],
    endDate: [''],
  });

  override columns: TableColumnModel<PromotionModel>[] = [
    { key: 'title', label: 'Title', type: 'string' },
    { key: 'startDate', label: 'Start Date', type: 'date' },
    { key: 'endDate', label: 'End Date', type: 'date' },
    { key: 'discount', label: 'Discount (%)', type: 'percentage' },
  ];

  override roleConfig = getRoleConfig(this.entityName);

  protected override entityService: EntityService<PromotionModel> = inject(PromotionService);
  protected override dialogService: EntityDialogServiceV2<PromotionModel> = inject(PromotionDialogService);
}
