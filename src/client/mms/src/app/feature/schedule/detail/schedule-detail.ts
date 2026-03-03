import { Component, input } from '@angular/core';
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { ScheduleModel } from '../../../model/schedule.model';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';

@Component({
  selector: 'app-schedule-detail',
  imports: [DetailText, FormatCellPipe],
  templateUrl: './schedule-detail.html',
  styleUrl: './schedule-detail.css',
})
export class ScheduleDetail {
  model = input.required<ScheduleModel>();
}
