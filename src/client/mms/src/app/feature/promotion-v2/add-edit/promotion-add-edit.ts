import { Component } from '@angular/core';
import { AddEditDialog } from '../../../shared/component-v2/dialog/add-edit-dialog/add-edit-dialog';
import { PromotionModel } from '../../../model/promotion/promotion.model';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { AddEditContainer } from "../../../shared/component-v2/dialog/add-edit-container/add-edit-container";
import { ImageField } from "../../../shared/component/form/image/image-field";
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { FormTextArea } from "../../../shared/component-v2/form/form-textarea/form-textarea";

@Component({
  selector: 'app-promotion-add-edit',
  imports: [AddEditContainer, ImageField, FormInput, FormTextArea],
  templateUrl: './promotion-add-edit.html',
  styleUrl: './promotion-add-edit.css',
})
export class PromotionAddEdit extends AddEditDialog<PromotionModel> {
  override form = this.formBuilder.nonNullable.group({
    title: [this.model?.title ?? '', [CustomValidators.required('promotion.title.required')]],
    startDate: [this.model?.startDate ?? '', [CustomValidators.required('promotion.startDate.required')]],
    endDate: [this.model?.endDate ?? '', [CustomValidators.required('promotion.endDate.required')]],
    description: [this.model?.description ?? ''],
    image: [this.model?.image ?? ''],
    discount: [this.model?.discount ?? 0, [CustomValidators.required('promotion.discount.required'), CustomValidators.range(5, 100, 'promotion.discount.invalid')]],
  });

  constructor() {
    super();
  }
}
