import { Component, input, OnChanges, signal, SimpleChanges } from '@angular/core';
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
export class SeatMap extends BaseFeature<SeatModel> implements OnChanges {
  override entityName = 'Seat';

  override contentCreateEdit = SeatCreateEdit;
  override contentDetail = SeatDetail;

  override roleConfig = getRoleConfig(this.entityName);

  showType = input(false);
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

  ngOnChanges(changes: SimpleChanges): void {
    this.loadMap();
  }

  override ngOnInit(): void {
    super.ngOnInit();

    this.loadMap();
  }

  private loadMap(): void {
    this.rows = Array.from({ length: this.columnLength() }, (_, i) => i + 1);
    this.columns = Array.from({ length: this.rowLength() }, (_, i) => i + 1);

    const seatMap = new Map<string, SeatModel>();

    this.seats().forEach(seat => {
      const key = `${seat.seatRow}-${seat.seatColumn}`;
      seatMap.set(key, seat);
    });

    this.seatMap.set(seatMap);
  }

  protected override getUpsertGroup(): FormGroup {
    return this.formBuilder.nonNullable.group({
      name: ['', [CustomValidators.required('seat.name.required')]],
      seatType: ['STANDARD'],
      seatRow: [1, [
        CustomValidators.size(1, this.rowLength(), 'seat.row.invalid'),
      ]],
      seatColumn: [1, [
        CustomValidators.size(1, this.columnLength(), 'seat.column.invalid'),
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
    if (!this.showType()) {
      return seat.reserved ? 'occupied' : 'seat';
    }

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
