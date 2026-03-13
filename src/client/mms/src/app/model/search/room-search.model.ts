import { SearchModel } from "../../shared/model/search.model";

export interface RoomSearchModel extends SearchModel {
  seatQuantityMin?: number;
  seatQuantityMax?: number;
}
