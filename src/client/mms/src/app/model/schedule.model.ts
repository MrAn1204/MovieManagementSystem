import { IdNameModel } from '../shared/model/id-name.model';

export interface ScheduleModel {
  id: string;
  showTime: string;
  movie: IdNameModel;
  room: IdNameModel;
}
