import { IdNameModel } from '../../shared/model/id-name.model';
import { AuditableEntityModel } from '../../shared/model/auditable-entity.model';

export interface ScheduleModel extends AuditableEntityModel {
  name: string;
  showTime: string;
  movie: IdNameModel;
  room: IdNameModel;
}
