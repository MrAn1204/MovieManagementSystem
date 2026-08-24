import { Component, inject } from '@angular/core';
import { DetailContainer } from "../../../shared/component-v2/dialog/detail-container/detail-container";
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { ScheduleDetailModel } from '../../../model/schedule/schedule-detail.model';
import { DetailDialogV2 } from '../../../shared/component-v2/dialog/detail-dialog/detail-dialog';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { SeatMapV2 } from "../../seat-v2/seat-map-v2/seat-map-v2";
import { MatDivider } from "@angular/material/divider";
import { DetailEntityService } from '../../../service/detail-entity.service';
import { ScheduleService } from '../../../service/schedule/schedule.service';

@Component({
  selector: 'app-schedule-detail-v2',
  imports: [DetailContainer, DetailText, FormatCellPipe, SeatMapV2, MatDivider],
  templateUrl: './schedule-detail-v2.html',
  styleUrl: './schedule-detail-v2.css',
})
export class ScheduleDetailV2 extends DetailDialogV2<ScheduleDetailModel> {
  protected override entityService: DetailEntityService<ScheduleDetailModel> = inject(ScheduleService);
}
