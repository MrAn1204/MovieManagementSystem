import { ScheduleModel } from './schedule.model';
import { IdNameModel } from '../shared/model/id-name.model';
import { BaseEntityModel } from '../shared/model/base-entity.model';

export interface TicketModel extends BaseEntityModel {
  schedule: ScheduleModel;
  seat: IdNameModel;
  promotion: IdNameModel | null;
  price: number;
  username: string;
  phoneNumber: string;
}
