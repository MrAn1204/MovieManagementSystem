import { Component, OnInit, signal } from '@angular/core';
import { AddEditDialog } from '../../../shared/component-v2/dialog/add-edit-dialog/add-edit-dialog';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { merge } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { SeatService } from '../../../service/seat/seat.service';
import { AddEditContainer } from "../../../shared/component-v2/dialog/add-edit-container/add-edit-container";
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { FormSelect } from "../../../shared/component-v2/form/form-select/form-select";
import { SeatDetailModel } from '../../../model/seat/seat-detail.model';

@Component({
  selector: 'app-seat-add-edit',
  imports: [AddEditContainer, FormInput, FormSelect],
  templateUrl: './seat-add-edit.html',
  styleUrl: './seat-add-edit.css',
})
export class SeatAddEdit extends AddEditDialog<SeatDetailModel> implements OnInit {
  override form = this.formBuilder.nonNullable.group({
    name: ['', [CustomValidators.required('seat.name.required')]],
    seatType: ['STANDARD', [CustomValidators.required('seat.type.required')]],
    seatRow: [1, [
      CustomValidators.required('seat.row.required'),
      CustomValidators.range(1, this.model?.room.rowLength ?? 1, 'seat.row.invalid'),
    ]],
    seatColumn: [1, [
      CustomValidators.required('seat.column.required'),
      CustomValidators.range(1, this.model?.room.columnLength ?? 1, 'seat.column.invalid')
    ]],
    roomId: [this.model?.room.id ?? ''],
  });

  enableNameAutofill = false;
  seatTypes = signal<FormOptionModel[]>([]);

  private get rowControl() {
    return this.form.controls.seatRow;
  }

  private get columnControl() {
    return this.form.controls.seatColumn;
  }

  constructor(private readonly seatService: SeatService) {
    super();

    merge(this.rowControl.valueChanges, this.columnControl.valueChanges)
      .pipe(takeUntilDestroyed())
      .subscribe(() => {
        if (this.enableNameAutofill) {
          this.updateSeatName();
        }
      });
  }

  ngOnInit(): void {
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
      this.form.controls.name.disable();
      this.updateSeatName();
    } else {
      this.form.controls.name.enable();
    }
  }

  private updateSeatName(): void {
    const row = Number(this.rowControl.value);
    const column = Number(this.columnControl.value);
    const name = this.buildSeatName(row, column);
    this.form.controls.name.setValue(name);

  }

  private buildSeatName(row: number, column: number): string {
    if (!Number.isInteger(row) || this.rowControl?.errors) return '';
    if (!Number.isInteger(column) || this.columnControl?.errors) return '';
    const rowLetter = String.fromCodePoint(64 + row);
    return `${rowLetter}${column}`;
  }
}
