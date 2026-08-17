import { AuditModel } from "../../shared/model/audit.model";
import { SeatModel } from "./seat.model";

export interface SeatDetailModel extends SeatModel {
  audit?: AuditModel;
}
