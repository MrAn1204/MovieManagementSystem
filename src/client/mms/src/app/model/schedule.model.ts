import { IdNameModel } from '../shared/model/id-name.model';
import { BaseEntityModel } from '../shared/model/base-entity.model';

export interface ScheduleModel extends BaseEntityModel {
  showTime: string;
  movie: IdNameModel;
  room: IdNameModel;
}
