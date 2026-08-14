import { Component, OnInit, inject } from '@angular/core';
import { AddEditDialog } from '../../../shared/component-v2/dialog/add-edit-dialog/add-edit-dialog';
import { RoomDetailModel } from '../../../model/room/room-detail.model';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { AddEditContainer } from "../../../shared/component-v2/dialog/add-edit-container/add-edit-container";
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { ConstraintService } from '../../../service/constraint.service';

@Component({
  selector: 'app-room-add-edit',
  imports: [AddEditContainer, FormInput],
  templateUrl: './room-add-edit.html',
  styleUrl: './room-add-edit.css',
})
export class RoomAddEdit extends AddEditDialog<RoomDetailModel> implements OnInit {
  private readonly constraintService = inject(ConstraintService);

  private get constraints() {
    return this.constraintService.get('ROW_MAX', 'COLUMN_MAX');
  }

  override form = this.formBuilder.nonNullable.group({
      name: ['', [CustomValidators.required('room.name.required')]],
      rowLength: [1, [
        CustomValidators.required('room.rowLength.required'),
        CustomValidators.min(1, 'room.rowLength.invalid'),
      ]],
      columnLength: [1, [
        CustomValidators.required('room.columnLength.required'),
        CustomValidators.min(1, 'room.columnLength.invalid'),
      ]],
    });

  protected override patchForm(): void {
    const model = this.model;
    if (!model) {
      return;
    }

    this.form.patchValue({
      name: model.name,
      rowLength: model.rowLength,
      columnLength: model.columnLength,
    });
  }

  constructor() {
    super();
  }

  ngOnInit(): void {
    const constraints = this.constraints;

    this.form.get('rowLength')?.addValidators([
      CustomValidators.max(constraints['ROW_MAX'], 'room.rowLength.max')
    ]);

    this.form.get('columnLength')?.addValidators([
      CustomValidators.max(constraints['COLUMN_MAX'], 'room.columnLength.max')
    ]);

    this.patchForm();
  }
}
