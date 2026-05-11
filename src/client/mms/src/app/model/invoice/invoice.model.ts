import { AuditableEntityModel } from '../../shared/model/auditable-entity.model';
import { TicketModel } from '../ticket/ticket.model';

export interface InvoiceModel extends AuditableEntityModel {
  name: string;
  totalMoney: number;
  addScore: number;
  useScore: number;
  discount: number;
  tickets: TicketModel[];
}
