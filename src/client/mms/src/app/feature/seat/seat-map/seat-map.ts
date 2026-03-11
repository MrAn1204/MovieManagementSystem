import { Component, input, signal } from '@angular/core';
import { SeatService } from '../../../service/seat/seat.service';
import { SeatModel } from '../../../model/seat.model';
import { NgClass } from '@angular/common';
import { BaseFeature } from '../../../shared/component/feature/base-feature';
import { FormGroup, Validators } from '@angular/forms';
import { getRoleConfig } from '../../../shared/config/role-config';
import { SeatCreateEdit } from '../create-edit/seat-create-edit';
import { SeatDetail } from '../detail/seat-detail';

@Component({
  selector: 'app-seat-map',
  imports: [NgClass],
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

  seatMap = signal(new Map<string, SeatModel>());
  rows: number[] = [];
  columns: number[] = [];

  constructor(private readonly seatService: SeatService) {
    super();
  }

  override ngOnInit(): void {
    super.ngOnInit();

    this.rows = Array.from({ length: this.columnLength() }, (_, i) => i + 1);
    this.columns = Array.from({ length: this.rowLength() }, (_, i) => i + 1);

    this.loadMap();
  }

  private loadMap(): void {
    this.seatService.getAllInRoom(this.roomId()).subscribe(seats => {
      const seatMap = new Map<string, SeatModel>();

      seats.forEach(seat => {
        const key = `${seat.seatRow}-${seat.seatColumn}`;
        seatMap.set(key, seat);
      });

      this.seatMap.set(seatMap);
    });
  }

  protected override getUpsertGroup(): FormGroup {
    return this.formBuilder.nonNullable.group({
      name: ['', [Validators.required]],
      seatType: ['STANDARD'],
      seatRow: [1, [Validators.min(1), Validators.max(this.rowLength())]],
      seatColumn: [1, [Validators.min(1), Validators.max(this.columnLength())]],
      roomId: [this.roomId()],
    });
  }
  protected override saveNew(): void {
    console.log(this.entityForm.value);

    this.seatService.create(this.entityForm.value).subscribe(() => {
      this.loadMap();
    });
  }

  protected override saveUpdate(id: string): void {
    this.seatService.update(id, this.entityForm.value).subscribe(() => {
      this.loadMap();
    });
  }

  protected override confirmDelete(id: string): void {
    this.seatService.delete(id).subscribe(() => {
      this.loadMap();
    });
  }

  override onEdit(id: string): void {
    this.seatService.getById(id).subscribe(res => {
      this.displayEdit(res);
    });
  }

  override onView(id: string): void {
    this.seatService.getById(id).subscribe(res => {
      this.displayInfo(res);
    });
  }

  override onDelete(id: string): void {
    this.displayDelete(id);
  }

  override patchEntityForm(model: SeatModel): void {
    this.entityForm.patchValue({
      ...model,
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
