import { ScheduleModel } from './schedule.model';
import { SeatModel } from './seat.model';
import { PromotionModel } from './promotion.model';

export interface TicketModel {
  id: string;
  schedule: ScheduleModel;
  seat: SeatModel;
  promotion: PromotionModel;
  price: number;
  username: string;
  phoneNumber: string;
}
