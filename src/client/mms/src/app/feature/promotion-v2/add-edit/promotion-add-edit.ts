import { Component, inject } from '@angular/core';
import { AddEditDialog } from '../../../shared/component-v2/dialog/add-edit-dialog/add-edit-dialog';
import { PromotionModel } from '../../../model/promotion/promotion.model';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { AddEditContainer } from "../../../shared/component-v2/dialog/add-edit-container/add-edit-container";
import { ImageField } from "../../../shared/component/form/image/image-field";
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { FormTextArea } from "../../../shared/component-v2/form/form-textarea/form-textarea";
import { DetailEntityService } from '../../../service/detail-entity.service';
import { PromotionService } from '../../../service/promotion/promotion.service';
import { MatError } from '@angular/material/select';

@Component({
  selector: 'app-promotion-add-edit',
  imports: [AddEditContainer, ImageField, FormInput, FormTextArea, MatError],
  templateUrl: './promotion-add-edit.html',
  styleUrl: './promotion-add-edit.css',
})
export class PromotionAddEdit extends AddEditDialog<PromotionModel> {
  protected override entityService: DetailEntityService<PromotionModel> = inject(PromotionService);

  override form = this.formBuilder.nonNullable.group({
    title: ['', [CustomValidators.required('promotion.title.required')]],
    startDate: ['', [CustomValidators.required('promotion.startDate.required')]],
    endDate: ['', [CustomValidators.required('promotion.endDate.required')]],
    description: [''],
    image: [''],
    discount: [0, [CustomValidators.required('promotion.discount.required'), CustomValidators.range(5, 100, 'promotion.discount.invalid')]],
  });

  constructor() {
    super();
  }

  protected override mapForm(model: PromotionModel): void {
    this.onReset({
      title: model.title,
      startDate: model.startDate,
      endDate: model.endDate,
      description: model.description,
      image: model.image,
      discount: model.discount,
    });
  }
}
