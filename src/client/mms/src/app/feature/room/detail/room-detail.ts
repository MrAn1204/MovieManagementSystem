import { Component, input } from '@angular/core';
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { RoomModel } from '../../../model/room.model';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';

@Component({
  selector: 'app-room-detail',
  imports: [DetailText, FormatCellPipe],
  templateUrl: './room-detail.html',
})
export class RoomDetail {
  model = input.required<RoomModel>();
}
