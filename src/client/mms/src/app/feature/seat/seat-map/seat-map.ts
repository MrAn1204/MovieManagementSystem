import { Component, input, OnChanges, OnInit, signal, SimpleChanges } from '@angular/core';
import { SeatService } from '../../../service/seat/seat.service';
import { SeatModel } from '../../../model/seat/seat.model';
import { NgClass } from '@angular/common';
import { BaseFeature } from '../../../shared/component/feature/base-feature';
import { getRoleConfig } from '../../../shared/config/role-config';
import { SeatCreateEdit } from '../create-edit/seat-create-edit';
import { SeatDetail } from '../detail/seat-detail';
import { MapDescription } from "../map-description/map-description";

@Component({
  selector: 'app-seat-map',
  imports: [NgClass, MapDescription],
  templateUrl: './seat-map.html',
  styleUrl: './seat-map.css',
})
export class SeatMap extends BaseFeature<SeatModel> implements OnChanges, OnInit {
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

  ngOnInit(): void {
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

  override displayAdd(): void {
    super.displayAdd({
      roomId: this.roomId(),
      rowLength: this.rowLength(),
      columnLength: this.columnLength()
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
