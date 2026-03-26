import { Component } from '@angular/core';
import { InputField } from '../../../shared/component/form/input/input-field';
import { ImageField } from '../../../shared/component/form/image/image-field';
import { Textarea } from '../../../shared/component/form/textarea/textarea-field';
import { ReactiveFormsModule } from '@angular/forms';
import { CreateEdit } from '../../../shared/component/create-edit/create-edit';
import { CreateEditDialog } from '../../../shared/component/dialog/create-edit/create-edit-dialog';
import { PromotionModel } from '../../../model/promotion/promotion.model';
import { ValidationError } from '../../../shared/component/form/error/validation-error';

@Component({
  selector: 'app-promotion-create-edit',
  imports: [InputField, ImageField, Textarea, ReactiveFormsModule, CreateEdit, ValidationError],
  templateUrl: './promotion-create-edit.html',
  styleUrl: './promotion-create-edit.css',
})
export class PromotionCreateEdit extends CreateEditDialog<PromotionModel> {
}
