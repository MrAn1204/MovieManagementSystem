import { Component } from '@angular/core';
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { ScheduleModel } from '../../../model/schedule.model';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { Detail } from "../../../shared/component/detail/detail-component/detail";
import { ScheduleCreateEdit } from '../create-edit/schedule-create-edit';
import { DetailDialog } from '../../../shared/component/dialog/detail/detail-dialog';

@Component({
  selector: 'app-schedule-detail',
  imports: [DetailText, FormatCellPipe, Detail],
  templateUrl: './schedule-detail.html',
  styleUrl: './schedule-detail.css',
})
export class ScheduleDetail extends DetailDialog<ScheduleModel> {
  protected override updateDialog = ScheduleCreateEdit;
}
