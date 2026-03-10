import { Component, inject } from '@angular/core';
import { InputField } from "../../../shared/component/form/input/input-field";
import { RoomModel } from '../../../model/room.model';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { BaseDialog } from '../../../shared/component/dialog/base/base-dialog';
import { DialogFormDataModel } from '../../../shared/model/dialog/dialog-form-data.model';
import { DIALOG_DATA } from '@angular/cdk/dialog';
import { CreateEdit } from "../../../shared/component/dialog/create-edit/create-edit";

@Component({
  selector: 'app-room-create-edit',
  imports: [InputField, ReactiveFormsModule, CreateEdit],
  templateUrl: './room-create-edit.html',
  styleUrl: './room-create-edit.css'
})
export class RoomCreateEdit extends BaseDialog {
  data: DialogFormDataModel<RoomModel> = inject(DIALOG_DATA);

  get form(): FormGroup {
    return this.data.form;
  }

  onSubmit(): void {
    this.data.form.markAllAsTouched();
    if (this.data.form.invalid) {
      return;
    }
    this.dialogService.triggerSave();
  }
}
