import { BaseEntityModel } from '../shared/model/base-entity.model';
import { IdNameModel } from '../shared/model/id-name.model';

export interface SeatModel extends BaseEntityModel {
  seatColumn: number;
  seatRow: number;
  seatType: string;
  name: string;
  room: IdNameModel;
}
