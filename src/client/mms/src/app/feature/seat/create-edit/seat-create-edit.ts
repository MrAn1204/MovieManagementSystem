import { Component } from '@angular/core';
import { CreateEdit } from "../../../shared/component/create-edit/create-edit";
import { InputField } from "../../../shared/component/form/input/input-field";
import { SeatModel } from '../../../model/seat/seat.model';
import { SelectField } from "../../../shared/component/form/select/select-field";
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { ReactiveFormsModule } from "@angular/forms";
import { ValidationError } from "../../../shared/component/form/error/validation-error";
import { merge } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop'
import { CreateEditDialog } from '../../../shared/component/dialog/create-edit/create-edit-dialog';
import { DialogDataModel } from '../../../shared/model/dialog/dialog-data.model';
import { CustomValidators } from '../../../shared/util/custom-validators';

interface SeatDialogDataModel extends DialogDataModel<SeatModel> {
  roomId?: string;
  rowLength?: number;
  columnLength?: number;
}

@Component({
  selector: 'app-seat-create-edit',
  imports: [CreateEdit, InputField, SelectField, ReactiveFormsModule, ValidationError],
  templateUrl: './seat-create-edit.html',
  styleUrl: './seat-create-edit.css',
})
export class SeatCreateEdit extends CreateEditDialog<SeatModel> {
  private readonly seatData = this.data as SeatDialogDataModel;
  override form = this.createForm();

  enableNameAutofill = false;

  seatTypes: FormOptionModel[] = [
    { label: 'Standard', value: 'STANDARD', selected: true },
    { label: 'Premium', value: 'PREMIUM' },
    { label: 'Couple', value: 'COUPLE' },
    { label: 'Accessible', value: 'ACCESSIBLE' },
  ];

  private get rowControl() {
    return this.form.get('seatRow');
  }

  private get columnControl() {
    return this.form.get('seatColumn');
  }

  constructor() {
    super();

    this.patchForm();

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

  override createForm() {
    return this.formBuilder.nonNullable.group({
      name: ['', [CustomValidators.required('seat.name.required')]],
      seatType: ['STANDARD'],
      seatRow: [1, [CustomValidators.size(1, this.seatData.rowLength ?? 1, 'seat.row.invalid')]],
      seatColumn: [1, [CustomValidators.size(1, this.seatData.columnLength ?? 1, 'seat.column.invalid')]],
      roomId: [this.seatData.roomId ?? ''],
    });
  }

  override patchForm(): void {
    const model = this.data.model;
    if (!model) {
      return;
    }

    this.form.patchValue({
      name: model.name,
      seatType: model.seatType,
      seatRow: model.seatRow,
      seatColumn: model.seatColumn,
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
}
