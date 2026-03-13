import { Component, viewChild } from '@angular/core';
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { RoomModel } from '../../../model/room.model';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { SeatMap } from "../../seat/seat-map/seat-map";
import { RoomCreateEdit } from '../create-edit/room-create-edit';
import { Detail } from "../../../shared/component/detail/detail-component/detail";
import { DetailDialog } from '../../../shared/component/dialog/detail/detail-dialog';

@Component({
  selector: 'app-room-detail',
  imports: [DetailText, FormatCellPipe, SeatMap, Detail],
  templateUrl: './room-detail.html',
  styleUrl: './room-detail.css'
})
export class RoomDetail extends DetailDialog<RoomModel> {
  protected override updateDialog = RoomCreateEdit;

  seatMap = viewChild<SeatMap>('seatMap');

  calculateMaxCapacity() {
    const maxRow = this.model?.rowLength || 0;
    const maxColumn = this.model?.columnLength || 0;
    return maxRow * maxColumn;
  }

  openAddSeat(): void {
    this.seatMap()?.onAdd();
  }
}
