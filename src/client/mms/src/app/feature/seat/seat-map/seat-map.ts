import { Component, input, OnInit, signal } from '@angular/core';
import { SeatService } from '../../../service/seat/seat.service';
import { SeatModel } from '../../../model/seat.model';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-seat-map',
  imports: [NgClass],
  templateUrl: './seat-map.html',
  styleUrl: './seat-map.css',
})
export class SeatMap implements OnInit {
  rowLength = input.required<number>();
  columnLength = input.required<number>();
  roomId = input.required<string>();

  seatMap = signal(new Map<string, SeatModel>());
  rows: number[] = [];
  columns: number[] = [];

  constructor(private readonly seatService: SeatService) { }

  ngOnInit(): void {
    this.rows = Array.from({ length: this.columnLength() }, (_, i) => i + 1);
    this.columns = Array.from({ length: this.rowLength() }, (_, i) => i + 1);

    this.seatService.getAllInRoom(this.roomId()).subscribe(seats => {
      const seatMap = new Map<string, SeatModel>();

      seats.forEach(seat => {
        const key = `${seat.seatRow}-${seat.seatColumn}`;
        seatMap.set(key, seat);
      });

      this.seatMap.set(seatMap);
    });
  }

  getSeat(row: number, column: number): SeatModel | undefined {
    const key = `${row}-${column}`;
    return this.seatMap().get(key);
  }

  selectSeat(seat: SeatModel): void {
    console.log(seat);
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
