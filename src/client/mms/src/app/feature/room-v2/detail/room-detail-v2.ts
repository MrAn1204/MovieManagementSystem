import { Component, inject, viewChild } from '@angular/core';
import { DetailContainer } from "../../../shared/component-v2/dialog/detail-container/detail-container";
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { RoomDetailModel } from '../../../model/room/room-detail.model';
import { DetailDialogV2 } from '../../../shared/component-v2/dialog/detail-dialog/detail-dialog';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { AuthService } from '../../../service/auth/auth.service';
import { ButtonV2 } from "../../../shared/component-v2/button/button";
import { SeatMapV2 } from "../../seat-v2/seat-map-v2/seat-map-v2";
import { SeatDialogService } from '../../../service/dialog-v2/seat/seat-dialog.service';
import { SeatDetailModel } from '../../../model/seat/seat-detail.model';
import { finalize } from 'rxjs';
import { MatDivider } from "@angular/material/divider";

@Component({
  selector: 'app-room-detail-v2',
  imports: [DetailContainer, DetailText, FormatCellPipe, ButtonV2, SeatMapV2, MatDivider],
  templateUrl: './room-detail-v2.html',
  styleUrl: './room-detail-v2.css',
})
export class RoomDetailV2 extends DetailDialogV2<RoomDetailModel> {
  seatMap = viewChild<SeatMapV2>('seatMap');

  private readonly authService = inject(AuthService);
  private readonly seatDialogService = inject(SeatDialogService);

  openAddSeat(): void {
    const seatData: Partial<SeatDetailModel> = {
      room: {
        id: this.model!.id,
        rowLength: this.model!.rowLength,
        columnLength: this.model!.columnLength,
        name: this.model!.name,
      },
    }

    this.hideSelf();

    this.seatDialogService.showAddEditDialog(undefined, { model: seatData })
      .pipe(finalize(() => this.onRefresh()))
      .subscribe();
  }

  canAddSeat(): boolean {
    return this.authService.includeRoles(['ADMIN']) && this.model!.currentCapacity < this.model!.maxCapacity;
  }
}
