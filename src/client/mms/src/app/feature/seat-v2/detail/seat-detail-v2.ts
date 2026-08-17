import { Component } from '@angular/core';
import { DetailContainer } from "../../../shared/component-v2/dialog/detail-container/detail-container";
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { DetailDialogV2 } from '../../../shared/component-v2/dialog/detail-dialog/detail-dialog';
import { SeatDetailModel } from '../../../model/seat/seat-detail.model';

@Component({
  selector: 'app-seat-detail-v2',
  imports: [DetailContainer, FormatCellPipe, DetailText],
  templateUrl: './seat-detail-v2.html',
  styleUrl: './seat-detail-v2.css',
})
export class SeatDetailV2 extends DetailDialogV2<SeatDetailModel> {}
