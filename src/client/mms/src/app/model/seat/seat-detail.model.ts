import { AuditModel } from "../../shared/model/audit.model";
import { RoomSummaryModel } from "../room/room-summary.model";
import { SeatModel } from "./seat.model";

export interface SeatDetailModel extends SeatModel {
  room: RoomSummaryModel;
  audit?: AuditModel;
}
