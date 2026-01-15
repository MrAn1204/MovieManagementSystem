import { ScheduleModel } from './schedule.model';
import { IdNameModel } from '../shared/model/id-name.model';

export interface TicketModel {
  id: string;
  schedule: ScheduleModel;
  seat: IdNameModel;
  promotion: IdNameModel | null;
  price: number;
  username: string;
  phoneNumber: string;
}
