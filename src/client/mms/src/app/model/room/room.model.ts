import { AuditableEntityModel } from '../../shared/model/auditable-entity.model';

export interface RoomModel extends AuditableEntityModel {
  rowLength: number;
  columnLength: number;
  maxCapacity: number;
  name: string;
  currentCapacity: number;
}
