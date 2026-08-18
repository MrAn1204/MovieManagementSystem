import { BaseEntityModel } from "../../shared/model/base-entity.model";

export interface RoomSummaryModel extends BaseEntityModel {
  name: string;
  rowLength: number;
  columnLength: number;
}
