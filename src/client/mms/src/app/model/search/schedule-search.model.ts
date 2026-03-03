import { SearchModel } from "../../shared/model/search.model";

export interface ScheduleSearchModel extends SearchModel {
  date?: string;
  minTime?: string;
  maxTime?: string;
  roomId?: string;
}
