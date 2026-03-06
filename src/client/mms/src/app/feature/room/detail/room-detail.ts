import { Component, input } from '@angular/core';
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { RoomModel } from '../../../model/room.model';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { SeatMap } from "../../seat/seat-map/seat-map";

@Component({
  selector: 'app-room-detail',
  imports: [DetailText, FormatCellPipe, SeatMap],
  templateUrl: './room-detail.html',
})
export class RoomDetail {
  model = input.required<RoomModel>();
}
