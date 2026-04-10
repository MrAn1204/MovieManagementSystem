import { Component } from '@angular/core';
import { InputField } from '../../../shared/component/form/input/input-field';
import { ImageField } from '../../../shared/component/form/image/image-field';
import { Textarea } from '../../../shared/component/form/textarea/textarea-field';
import { ReactiveFormsModule } from '@angular/forms';
import { CreateEdit } from '../../../shared/component/create-edit/create-edit';
import { CreateEditDialog } from '../../../shared/component/dialog/create-edit/create-edit-dialog';
import { PromotionModel } from '../../../model/promotion/promotion.model';
import { ValidationError } from '../../../shared/component/form/error/validation-error';
import { CustomValidators } from '../../../shared/util/custom-validators';

@Component({
  selector: 'app-promotion-create-edit',
  imports: [InputField, ImageField, Textarea, ReactiveFormsModule, CreateEdit, ValidationError],
  templateUrl: './promotion-create-edit.html',
  styleUrl: './promotion-create-edit.css',
})
export class PromotionCreateEdit extends CreateEditDialog<PromotionModel> {
  override form = this.createForm();

  override createForm() {
    return this.formBuilder.nonNullable.group({
      title: ['', [CustomValidators.required('promotion.title.required')]],
      startDate: ['', [CustomValidators.required('promotion.startDate.required')]],
      endDate: ['', [CustomValidators.required('promotion.endDate.required')]],
      description: [''],
      image: [''],
      discount: [0, [CustomValidators.required('promotion.discount.required'), CustomValidators.range(5, 100, 'promotion.discount.invalid')]],
    });
  }

  override patchForm(): void {
    const model = this.data.model;
    if (!model) {
      return;
    }

    this.form.patchValue({
      ...model,
    });
  }
}
