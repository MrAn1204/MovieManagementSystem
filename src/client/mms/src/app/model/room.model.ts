import { IdNameModel } from '../shared/model/id-name.model';
import { BaseEntityModel } from '../shared/model/base-entity.model';

export interface RoomModel extends BaseEntityModel {
  rowLength: number;
  columnLength: number;
  maxCapacity: number;
  name: string;
  seats: IdNameModel[];
}
