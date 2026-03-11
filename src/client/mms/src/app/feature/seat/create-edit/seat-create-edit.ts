import { Component, inject } from '@angular/core';
import { BaseDialog } from '../../../shared/component/dialog/base/base-dialog';
import { CreateEdit } from "../../../shared/component/dialog/create-edit/create-edit";
import { InputField } from "../../../shared/component/form/input/input-field";
import { DialogFormDataModel } from '../../../shared/model/dialog/dialog-form-data.model';
import { DIALOG_DATA } from '@angular/cdk/dialog';
import { SeatModel } from '../../../model/seat.model';
import { SelectField } from "../../../shared/component/form/select/select-field";
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { ReactiveFormsModule } from "@angular/forms";

@Component({
  selector: 'app-seat-create-edit',
  imports: [CreateEdit, InputField, SelectField, ReactiveFormsModule],
  templateUrl: './seat-create-edit.html',
  styleUrl: './seat-create-edit.css',
})
export class SeatCreateEdit extends BaseDialog {
  data: DialogFormDataModel<SeatModel> = inject(DIALOG_DATA);

  seatTypes: FormOptionModel[] = [
    { label: 'Standard', value: 'STANDARD', selected: true },
    { label: 'Premium', value: 'PREMIUM' },
    { label: 'Couple', value: 'COUPLE' },
    { label: 'Accessible', value: 'ACCESSIBLE' },
  ];

  get form() {
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
