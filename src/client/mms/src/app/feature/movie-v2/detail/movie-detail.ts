import { Component, inject } from '@angular/core';
import { DetailContainer } from "../../../shared/component-v2/dialog/detail-container/detail-container";
import { DetailImage } from "../../../shared/component/detail/detail-image/detail-image";
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { MovieDetailModel } from '../../../model/movie/movie-detail.model';
import { DetailDialogV2 } from '../../../shared/component-v2/dialog/detail-dialog/detail-dialog';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { ScheduleSummaryModel } from '../../../model/schedule/schedule-summary.model';
import { TableMenuOutput, TableV2 } from "../../../shared/component-v2/table/table";
import { ScheduleDialogService } from '../../../service/dialog-v2/schedule/schedule-dialog.service';
import { finalize } from 'rxjs';
import { MenuItem } from '../../../shared/component-v2/menu/menu';
import { TicketDialogService } from '../../../service/dialog-v2/ticket/ticket-dialog.service';

@Component({
  selector: 'app-movie-detail',
  imports: [DetailContainer, DetailImage, DetailText, FormatCellPipe, TableV2],
  templateUrl: './movie-detail.html',
  styleUrl: './movie-detail.css',
})
export class MovieDetailV2 extends DetailDialogV2<MovieDetailModel> {
  schedulesColumns: TableColumnModel<ScheduleSummaryModel>[] = [
    { key: 'showTime', label: 'Show Time', type: 'datetime' },
    { key: 'roomName', label: 'Room' },
  ];

  private readonly scheduleDialog = inject(ScheduleDialogService);
  private readonly ticketDialog = inject(TicketDialogService);

  scheduleRowMenu: MenuItem[] = [
    { label: 'View', icon: 'visibility', action: 'view' },
    { label: 'Book Ticket', icon: 'confirmation_number', action: 'book_ticket' },
  ];

  viewSchedule(scheduleId: string): void {
    this.hideSelf();

    this.scheduleDialog.showDetailDialog(scheduleId)
      .pipe(finalize(() => this.showSelf()))
      .subscribe();
  }

  handleMenuAction(event: TableMenuOutput): void {
    const action = event.action;
    const item = event.item;

    if (action === 'view' && item) {
      this.viewSchedule(item.id);
    } else if (action === 'book_ticket' && item) {
      this.handleBooking(item.id);
    }
  }

  handleBooking(scheduleId: string): void {
    this.hideSelf();

    this.ticketDialog.showBookTicketDialog(scheduleId)
      .pipe(finalize(() => this.showSelf()))
      .subscribe();
  }
}
