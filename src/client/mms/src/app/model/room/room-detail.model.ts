import { AuditModel } from '../../shared/model/audit.model';
import { SeatModel } from '../seat/seat.model';
import { RoomModel } from './room.model';

export interface RoomDetailModel extends RoomModel {
  seats: SeatModel[];
  audit?: AuditModel;
}
