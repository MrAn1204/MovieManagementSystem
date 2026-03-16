import { IdNameModel } from '../../shared/model/id-name.model';
import { TicketModel } from './ticket.model';

export interface TicketDetailModel extends TicketModel {
  showTime: string;
  room: IdNameModel;
  seat: IdNameModel;
  promotion: IdNameModel;
  price: number;
}
