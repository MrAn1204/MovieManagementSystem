import { BaseEntityModel } from '../../shared/model/base-entity.model';

export interface SeatModel extends BaseEntityModel {
  seatColumn: number;
  seatRow: number;
  seatType: string;
  name: string;
  linkedSeatId: string | null;
  reserved: boolean | null;
}
