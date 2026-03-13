import { Component } from '@angular/core';
import { Detail } from "../../../shared/component/detail/detail-component/detail";
import { SeatModel } from '../../../model/seat.model';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { SeatCreateEdit } from '../create-edit/seat-create-edit';
import { DetailDialog } from '../../../shared/component/dialog/detail/detail-dialog';

@Component({
  selector: 'app-seat-detail',
  imports: [Detail, FormatCellPipe, DetailText],
  templateUrl: './seat-detail.html',
  styleUrl: './seat-detail.css',
})
export class SeatDetail extends DetailDialog<SeatModel> {
  protected override updateDialog = SeatCreateEdit;
}
