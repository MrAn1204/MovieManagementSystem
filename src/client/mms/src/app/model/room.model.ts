import { SeatModel } from './seat.model';

export interface RoomModel {
  id: string;
  seatQuantity: number;
  name: string;
  seats: SeatModel[];
}
