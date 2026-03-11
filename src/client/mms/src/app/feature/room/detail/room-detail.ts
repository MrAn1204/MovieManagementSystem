import { Component, inject, viewChild } from '@angular/core';
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { RoomModel } from '../../../model/room.model';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { SeatMap } from "../../seat/seat-map/seat-map";
import { BaseDialog } from '../../../shared/component/dialog/base/base-dialog';
import { DialogDataModel } from '../../../shared/model/dialog/dialog-data.model';
import { DIALOG_DATA } from '@angular/cdk/dialog';
import { PopupModal } from '../../../shared/component/dialog/popup-modal/popup-modal';
import { RoomCreateEdit } from '../create-edit/room-create-edit';
import { Detail } from "../../../shared/component/dialog/detail/detail";

@Component({
  selector: 'app-room-detail',
  imports: [DetailText, FormatCellPipe, SeatMap, Detail],
  templateUrl: './room-detail.html',
  styleUrl: './room-detail.css'
})
export class RoomDetail extends BaseDialog {
  data: DialogDataModel<RoomModel> = inject(DIALOG_DATA);

  seatMap = viewChild<SeatMap>('seatMap');

  get model(): RoomModel {
    return this.data.model as RoomModel;
  }

  openUpdate(): void {
    this.dialogService.triggerOpen(RoomCreateEdit)
  }

  openDelete(): void {
    this.dialogService.triggerOpen(PopupModal);
  }

  openAddSeat(): void {
    this.seatMap()?.onAdd();
  }
}
