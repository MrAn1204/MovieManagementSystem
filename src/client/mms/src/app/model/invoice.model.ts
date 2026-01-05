import { TicketModel } from './ticket.model';

export interface InvoiceModel {
  id: string;
  totalMoney: number;
  addScore: number;
  useScore: number;
  discount: number;
  tickets: TicketModel[];
}
