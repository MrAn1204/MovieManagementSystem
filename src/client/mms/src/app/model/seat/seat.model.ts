import { AuditableEntityModel } from '../../shared/model/auditable-entity.model';

export interface SeatModel extends AuditableEntityModel {
  seatColumn: number;
  seatRow: number;
  seatType: string;
  name: string;
  linkedSeatId: string | null;
  reserved: boolean | null;
}
