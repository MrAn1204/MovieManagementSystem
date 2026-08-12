import { AuditModel } from '../../shared/model/audit.model';
import { SeatModel } from '../seat/seat.model';
import { ScheduleModel } from './schedule.model';

export interface ScheduleDetailModel extends ScheduleModel {
  seats: SeatModel[];
  rowLength: number;
  columnLength: number;
  audit?: AuditModel;
}
