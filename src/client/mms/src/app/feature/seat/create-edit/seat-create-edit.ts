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
import { ValidationError } from "../../../shared/component/form/error/validation-error";
import { merge } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop'

@Component({
  selector: 'app-seat-create-edit',
  imports: [CreateEdit, InputField, SelectField, ReactiveFormsModule, ValidationError],
  templateUrl: './seat-create-edit.html',
  styleUrl: './seat-create-edit.css',
})
export class SeatCreateEdit extends BaseDialog {
  data: DialogFormDataModel<SeatModel> = inject(DIALOG_DATA);
  enableNameAutofill = false;

  seatTypes: FormOptionModel[] = [
    { label: 'Standard', value: 'STANDARD', selected: true },
    { label: 'Premium', value: 'PREMIUM' },
    { label: 'Couple', value: 'COUPLE' },
    { label: 'Accessible', value: 'ACCESSIBLE' },
  ];

  get form() {
    return this.data.form;
  }

  private get rowControl() {
    return this.form.get('seatRow');
  }

  private get columnControl() {
    return this.form.get('seatColumn');
  }

  constructor() {
    super();

    if (!this.rowControl || !this.columnControl) {
      return;
    }

    merge(this.rowControl.valueChanges, this.columnControl.valueChanges)
    .pipe(takeUntilDestroyed())
    .subscribe(() => {

      if (this.enableNameAutofill) {
        this.updateSeatName();
      }
    });
  }

  onAutoFillNameChange(event: Event): void {
    this.enableNameAutofill = (event.target as HTMLInputElement).checked;

    if (this.enableNameAutofill) {
      this.updateSeatName();
    }
  }

  private updateSeatName(): void {
    const row = Number(this.rowControl?.value);
    const column = Number(this.columnControl?.value);
    const name = this.buildSeatName(row, column);

    this.form.get('name')?.setValue(name);
  }

  private buildSeatName(row: number, column: number): string {
    if (!Number.isInteger(row) || this.rowControl?.errors) {
      return '';
    }

    if (!Number.isInteger(column) || this.columnControl?.errors) {
      return '';
    }

    const rowLetter = String.fromCodePoint(64 + row);
    return `${rowLetter}${column}`;
  }

  onSubmit(): void {
    this.data.form.markAllAsTouched();
    if (this.data.form.invalid) {
      return;
    }
    this.dialogService.triggerSave();
  }
}
