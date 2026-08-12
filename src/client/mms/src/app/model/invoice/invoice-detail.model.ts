import { AuditModel } from "../../shared/model/audit.model";
import { TicketModel } from "../ticket/ticket.model";
import { UserSummaryModel } from "../user/user-summary.model";
import { InvoiceModel } from "./invoice.model";

export interface InvoiceDetailModel extends InvoiceModel {
  tickets: TicketModel[];
  user: UserSummaryModel;
  audit?: AuditModel;
}
