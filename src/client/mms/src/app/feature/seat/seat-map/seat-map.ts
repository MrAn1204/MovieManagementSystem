import { Component, input, signal } from '@angular/core';
import { SeatService } from '../../../service/seat/seat.service';
import { SeatModel } from '../../../model/seat/seat.model';
import { NgClass } from '@angular/common';
import { BaseFeature } from '../../../shared/component/feature/base-feature';
import { FormGroup } from '@angular/forms';
import { getRoleConfig } from '../../../shared/config/role-config';
import { SeatCreateEdit } from '../create-edit/seat-create-edit';
import { SeatDetail } from '../detail/seat-detail';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { MapDescription } from "../map-description/map-description";

@Component({
  selector: 'app-seat-map',
  imports: [NgClass, MapDescription],
  templateUrl: './seat-map.html',
  styleUrl: './seat-map.css',
})
export class SeatMap extends BaseFeature<SeatModel> {
  override entityName = 'Seat';

  override contentCreateEdit = SeatCreateEdit;
  override contentDetail = SeatDetail;

  override roleConfig = getRoleConfig(this.entityName);

  rowLength = input.required<number>();
  columnLength = input.required<number>();
  roomId = input.required<string>();
  seats = input.required<SeatModel[]>();

  seatMap = signal(new Map<string, SeatModel>());
  rows: number[] = [];
  columns: number[] = [];

  constructor(seatService: SeatService) {
    super(seatService);
  }

  override ngOnInit(): void {
    super.ngOnInit();

    this.rows = Array.from({ length: this.columnLength() }, (_, i) => i + 1);
    this.columns = Array.from({ length: this.rowLength() }, (_, i) => i + 1);

    this.loadMap();
  }

  private loadMap(): void {
    const seatMap = new Map<string, SeatModel>();

    this.seats().forEach(seat => {
      const key = `${seat.seatRow}-${seat.seatColumn}`;
      seatMap.set(key, seat);
    });

    this.seatMap.set(seatMap);
  }

  protected override getUpsertGroup(): FormGroup {
    return this.formBuilder.nonNullable.group({
      name: ['', [CustomValidators.required('Name is required')]],
      seatType: ['STANDARD'],
      seatRow: [1, [
        CustomValidators.min(1, 'Row must be at least 1'),
        CustomValidators.max(this.rowLength(), `Row must be at most ${this.rowLength()}`)
      ]],
      seatColumn: [1, [
        CustomValidators.min(1, 'Column must be at least 1'),
        CustomValidators.max(this.columnLength(), `Column must be at most ${this.columnLength()}`)
      ]],
      roomId: [this.roomId()],
    });
  }

  override patchEntityForm(model: SeatModel): void {
    this.entityForm.patchValue({
      name: model.name,
      seatType: model.seatType,
      seatRow: model.seatRow,
      seatColumn: model.seatColumn,
    });
  }

  getSeat(row: number, column: number): SeatModel | undefined {
    const key = `${row}-${column}`;
    return this.seatMap().get(key);
  }

  getType(seat: SeatModel): string {
    if (seat.seatType === 'STANDARD') {
      return 'seat';
    } else if (seat.seatType === 'PREMIUM') {
      return 'premium';
    } else if (seat.seatType === 'COUPLE') {
      return 'couple';
    } else if (seat.seatType === 'ACCESSIBLE') {
      return 'accessible';
    } else {
      return '';
    }
  }
}
