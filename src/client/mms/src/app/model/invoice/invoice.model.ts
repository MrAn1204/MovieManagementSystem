import { BaseEntityModel } from '../../shared/model/base-entity.model';
import { TicketModel } from '../ticket/ticket.model';

export interface InvoiceModel extends BaseEntityModel {
  name: string;
  totalMoney: number;
  addScore: number;
  useScore: number;
  discount: number;
  tickets: TicketModel[];
}
