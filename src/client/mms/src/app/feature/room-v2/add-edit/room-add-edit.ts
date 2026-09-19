import { Component, inject } from '@angular/core';
import { AddEditDialog } from '../../../shared/component-v2/dialog/add-edit-dialog/add-edit-dialog';
import { RoomDetailModel } from '../../../model/room/room-detail.model';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { AddEditContainer } from "../../../shared/component-v2/dialog/add-edit-container/add-edit-container";
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { ConstraintService } from '../../../service/constraint.service';
import { DetailEntityService } from '../../../service/detail-entity.service';
import { RoomService } from '../../../service/room/room.service';
import { MatError } from '@angular/material/select';

@Component({
  selector: 'app-room-add-edit',
  imports: [AddEditContainer, FormInput, MatError],
  templateUrl: './room-add-edit.html',
  styleUrl: './room-add-edit.css',
})
export class RoomAddEdit extends AddEditDialog<RoomDetailModel> {
  protected override entityService: DetailEntityService<RoomDetailModel> = inject(RoomService);

  private readonly constraintService = inject(ConstraintService);

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

  constructor() {
    super();
  }

  override ngOnInit(): void {
    super.ngOnInit();

    const constraints = this.constraintService.get('ROW_MAX', 'COLUMN_MAX');

    this.form.controls.rowLength.addValidators([
      CustomValidators.max(constraints['ROW_MAX'], 'room.rowLength.max')
    ]);

    this.form.controls.columnLength.addValidators([
      CustomValidators.max(constraints['COLUMN_MAX'], 'room.columnLength.max')
    ]);
  }

  protected override mapForm(model: RoomDetailModel): void {
    this.onReset({
      name: model.name,
      rowLength: model.rowLength,
      columnLength: model.columnLength,
    });
  }
}
