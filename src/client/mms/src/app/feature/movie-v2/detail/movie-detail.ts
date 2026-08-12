import { Component } from '@angular/core';
import { DetailContainer } from "../../../shared/component-v2/dialog/detail-container/detail-container";
import { DetailImage } from "../../../shared/component/detail/detail-image/detail-image";
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { MovieDetailModel } from '../../../model/movie/movie-detail.model';
import { DetailDialogV2 } from '../../../shared/component-v2/dialog/detail-dialog/detail-dialog';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { ScheduleSummaryModel } from '../../../model/schedule/schedule-summary.model';
import { Button } from "../../../shared/component/button/button";
import { TableV2 } from "../../../shared/component-v2/table/table";

@Component({
  selector: 'app-movie-detail',
  imports: [DetailContainer, DetailImage, DetailText, FormatCellPipe, Button, TableV2],
  templateUrl: './movie-detail.html',
  styleUrl: './movie-detail.css',
})
export class MovieDetailV2 extends DetailDialogV2<MovieDetailModel> {
  schedulesColumns: TableColumnModel<ScheduleSummaryModel>[] = [
    { key: 'showTime', label: 'Show Time', type: 'datetime' },
    { key: 'roomName', label: 'Room' },
  ];
}
