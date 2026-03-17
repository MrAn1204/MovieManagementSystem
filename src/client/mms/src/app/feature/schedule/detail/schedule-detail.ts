import { Component } from '@angular/core';
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { Detail } from "../../../shared/component/detail/detail-component/detail";
import { ScheduleCreateEdit } from '../create-edit/schedule-create-edit';
import { DetailDialog } from '../../../shared/component/dialog/detail/detail-dialog';
import { ScheduleDetailModel } from '../../../model/schedule/schedule-detail.model';
import { SeatMap } from "../../seat/seat-map/seat-map";

@Component({
  selector: 'app-schedule-detail',
  imports: [DetailText, FormatCellPipe, Detail, SeatMap],
  templateUrl: './schedule-detail.html',
  styleUrl: './schedule-detail.css',
})
export class ScheduleDetail extends DetailDialog<ScheduleDetailModel> {
  protected override updateDialog = ScheduleCreateEdit;
}
