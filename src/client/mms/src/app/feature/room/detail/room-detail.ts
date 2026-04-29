import { Component, inject, viewChild } from '@angular/core';
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { SeatMap } from "../../seat/seat-map/seat-map";
import { RoomCreateEdit } from '../create-edit/room-create-edit';
import { Detail } from "../../../shared/component/detail/detail-component/detail";
import { DetailDialog } from '../../../shared/component/dialog/detail/detail-dialog';
import { RoomDetailModel } from '../../../model/room/room-detail.model';
import { Button } from "../../../shared/component/button/button";
import { AuthService } from '../../../service/auth/auth.service';

@Component({
  selector: 'app-room-detail',
  imports: [DetailText, FormatCellPipe, SeatMap, Detail, Button],
  templateUrl: './room-detail.html',
  styleUrl: './room-detail.css'
})
export class RoomDetail extends DetailDialog<RoomDetailModel> {
  protected override updateDialog = RoomCreateEdit;

  seatMap = viewChild<SeatMap>('seatMap');

  private readonly authService = inject(AuthService);

  openAddSeat(): void {
    this.seatMap()?.onAdd();
  }

  canAddSeat(): boolean {
    return this.authService.includeRoles(['ADMIN']) && this.model!.currentCapacity < this.model!.maxCapacity;
  }
}
