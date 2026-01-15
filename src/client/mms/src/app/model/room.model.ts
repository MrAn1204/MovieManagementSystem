import { IdNameModel } from '../shared/model/id-name.model';

export interface RoomModel {
  id: string;
  seatQuantity: number;
  name: string;
  seats: IdNameModel[];
}
