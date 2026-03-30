import { IdNameModel } from '../../shared/model/id-name.model';
import { ScheduleModel } from '../schedule/schedule.model';
import { TicketModel } from './ticket.model';

export interface TicketDetailModel extends TicketModel {
  schedule: ScheduleModel;
  room: IdNameModel;
  seat: IdNameModel;
  promotion: IdNameModel | null;
}
