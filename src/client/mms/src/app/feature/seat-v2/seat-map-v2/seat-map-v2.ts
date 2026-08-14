import { Component, inject, input, OnChanges, OnInit, output, signal, SimpleChanges } from '@angular/core';
import { SeatService } from '../../../service/seat/seat.service';
import { SeatModel } from '../../../model/seat/seat.model';
import { NgClass } from '@angular/common';
import { BaseFeatureV2 } from '../../../shared/component-v2/feature/base-feature';
import { getRoleConfig } from '../../../shared/config/role-config';
import { SeatAddEdit } from '../add-edit/seat-add-edit';
import { SeatDetailV2 } from '../detail/seat-detail-v2';
import { MapDescription } from "../../seat/map-description/map-description";
import { EntityService } from '../../../service/entity.service';
import { SeatDialogService } from '../../../service/dialog-v2/seat/seat-dialog.service';
import { EntityDialogServiceV2 } from '../../../service/dialog-v2/entity-dialog.service';

@Component({
  selector: 'app-seat-map-v2',
  imports: [NgClass, MapDescription],
  templateUrl: './seat-map-v2.html',
  styleUrl: './seat-map-v2.css',
})
export class SeatMapV2 extends BaseFeatureV2<SeatModel> implements OnChanges, OnInit {
  override entityName = 'Seat';
  override contentAddEdit = SeatAddEdit;
  override contentDetail = SeatDetailV2;
  override roleConfig = getRoleConfig(this.entityName);

  showType = input(false);
  rowLength = input.required<number>();
  columnLength = input.required<number>();
  roomId = input.required<string>();
  seats = input.required<SeatModel[]>();

  seatMap = signal(new Map<string, SeatModel>());
  rows: number[] = [];
  columns: number[] = [];

  reloadDialog = output<void>();

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

  getSeat(row: number, column: number): SeatModel | undefined {
    const key = `${row}-${column}`;
    return this.seatMap().get(key);
  }

  getType(seat: SeatModel): string {
    if (!this.showType()) {
      return seat.reserved ? 'occupied' : 'seat';
    }
    if (seat.seatType === 'STANDARD') return 'seat';
    if (seat.seatType === 'PREMIUM') return 'premium';
    if (seat.seatType === 'COUPLE') return 'couple';
    if (seat.seatType === 'ACCESSIBLE') return 'accessible';
    return '';
  }

  protected override entityService: EntityService<SeatModel> = inject(SeatService);
  protected override dialogService: EntityDialogServiceV2<SeatModel> = inject(SeatDialogService);

}
