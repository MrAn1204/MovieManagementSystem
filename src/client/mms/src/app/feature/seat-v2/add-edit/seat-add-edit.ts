import { Component, inject, OnInit, signal } from '@angular/core';
import { AddEditDialog } from '../../../shared/component-v2/dialog/add-edit-dialog/add-edit-dialog';
import { SeatModel } from '../../../model/seat/seat.model';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { merge } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { DialogFormDataModel } from '../../../shared/model/dialog/dialog-form-data.model';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { SeatService } from '../../../service/seat/seat.service';
import { AddEditContainer } from "../../../shared/component-v2/dialog/add-edit-container/add-edit-container";
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { FormSelect } from "../../../shared/component-v2/form/form-select/form-select";
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

interface SeatDialogDataModel extends DialogFormDataModel<SeatModel> {
  roomId?: string;
  rowLength?: number;
  columnLength?: number;
}

@Component({
  selector: 'app-seat-add-edit',
  imports: [AddEditContainer, FormInput, FormSelect],
  templateUrl: './seat-add-edit.html',
  styleUrl: './seat-add-edit.css',
})
export class SeatAddEdit extends AddEditDialog<SeatModel> implements OnInit {
  private readonly seatData = inject<SeatDialogDataModel>(MAT_DIALOG_DATA);

  override form = this.formBuilder.nonNullable.group({
    name: ['', [CustomValidators.required('seat.name.required')]],
    seatType: ['STANDARD'],
    seatRow: [1, [CustomValidators.range(1, 1, 'seat.row.invalid')]],
    seatColumn: [1, [CustomValidators.range(1, 1, 'seat.column.invalid')]],
    roomId: [''],
  });

  enableNameAutofill = false;
  seatTypes = signal<FormOptionModel[]>([]);

  private get rowControl() { return this.form.get('seatRow'); }
  private get columnControl() { return this.form.get('seatColumn'); }

  constructor(private readonly seatService: SeatService) {
    super();

    merge(this.rowControl!.valueChanges, this.columnControl!.valueChanges)
      .pipe(takeUntilDestroyed())
      .subscribe(() => {
        if (this.enableNameAutofill) {
          this.updateSeatName();
        }
      });
  }

  ngOnInit(): void {
    // Set row/column validators using dialog data
    const rowLength = this.seatData.rowLength ?? 1;
    const columnLength = this.seatData.columnLength ?? 1;

    this.form.get('seatRow')?.setValidators([CustomValidators.range(1, rowLength, 'seat.row.invalid')]);
    this.form.get('seatColumn')?.setValidators([CustomValidators.range(1, columnLength, 'seat.column.invalid')]);
    this.form.patchValue({ roomId: this.seatData.roomId ?? '' });

    this.patchForm();

    this.seatService.getSeatTypes().subscribe(res => {
      this.seatTypes.set(Object.keys(res).map(key => ({
        label: key,
        value: key,
      })));
    });
  }

  protected override patchForm(): void {
    const model = this.model;
    if (!model) return;

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
    if (!Number.isInteger(row) || this.rowControl?.errors) return '';
    if (!Number.isInteger(column) || this.columnControl?.errors) return '';
    const rowLetter = String.fromCodePoint(64 + row);
    return `${rowLetter}${column}`;
  }
}
