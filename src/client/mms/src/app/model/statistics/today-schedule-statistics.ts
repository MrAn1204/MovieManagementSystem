import { BaseEntityModel } from "../../shared/model/base-entity.model";

export interface TodayScheduleStatisticsModel extends BaseEntityModel {
  id: string;
  movieName: string;
  showTime: string;
  roomName: string;
}
