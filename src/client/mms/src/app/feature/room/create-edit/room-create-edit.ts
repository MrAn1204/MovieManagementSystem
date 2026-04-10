import { Component, inject } from '@angular/core';
import { InputField } from "../../../shared/component/form/input/input-field";
import { ReactiveFormsModule } from '@angular/forms';
import { CreateEdit } from "../../../shared/component/create-edit/create-edit";
import { CreateEditDialog } from '../../../shared/component/dialog/create-edit/create-edit-dialog';
import { RoomDetailModel } from '../../../model/room/room-detail.model';
import { ValidationError } from "../../../shared/component/form/error/validation-error";
import { CustomValidators } from '../../../shared/util/custom-validators';
import { ConstraintService } from '../../../service/constraint.service';

@Component({
  selector: 'app-room-create-edit',
  imports: [InputField, ReactiveFormsModule, CreateEdit, ValidationError],
  templateUrl: './room-create-edit.html',
  styleUrl: './room-create-edit.css'
})
export class RoomCreateEdit extends CreateEditDialog<RoomDetailModel> {
  private readonly constraintService = inject(ConstraintService);
  override form = this.createForm();

  override createForm() {
    const constraints = this.constraintService.get('ROW_MAX', 'COLUMN_MAX');

    return this.formBuilder.nonNullable.group({
      name: this.formBuilder.control<string | null>(null, [CustomValidators.required('room.name.required')]),
      rowLength: [1, [
        CustomValidators.required('room.rowLength.required'),
        CustomValidators.min(1, 'room.rowLength.invalid'),
        CustomValidators.max(constraints['ROW_MAX'], 'room.rowLength.max')
      ]],
      columnLength: [1, [
        CustomValidators.required('room.columnLength.required'),
        CustomValidators.min(1, 'room.columnLength.invalid'),
        CustomValidators.max(constraints['COLUMN_MAX'], 'room.columnLength.max')
      ]],
    });
  }

  override patchForm(): void {
    const model = this.data.model;
    if (!model) {
      return;
    }

    this.form.patchValue({
      name: model.name,
      rowLength: model.rowLength,
      columnLength: model.columnLength,
    });
  }
}
