import { AuditModel } from "../../shared/model/audit.model";
import { ScheduleSummaryModel } from "../schedule/schedule-summary.model";
import { MovieModel } from "./movie.model";

export interface MovieDetailModel extends MovieModel {
  content: string;
  thumbnail: string;
  schedules: ScheduleSummaryModel[];
  audit?: AuditModel;
}
