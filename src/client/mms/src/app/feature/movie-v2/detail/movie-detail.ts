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
import { switchMap } from 'rxjs';
import { COMMON_MENU_ITEMS, MenuItem } from '../../../shared/component-v2/menu/menu';
import { TicketDialogService } from '../../../service/dialog-v2/ticket/ticket-dialog.service';
import { DetailEntityService } from '../../../service/detail-entity.service';
import { MovieService } from '../../../service/movie/movie.service';
import { ScheduleService } from '../../../service/schedule/schedule.service';

@Component({
  selector: 'app-movie-detail',
  imports: [DetailContainer, DetailImage, DetailText, FormatCellPipe, TableV2],
  templateUrl: './movie-detail.html',
  styleUrl: './movie-detail.css',
})
export class MovieDetailV2 extends DetailDialogV2<MovieDetailModel> {
  protected override entityService: DetailEntityService<MovieDetailModel> = inject(MovieService);

  schedulesColumns: TableColumnModel<ScheduleSummaryModel>[] = [
    { key: 'showTime', label: 'Show Time', type: 'datetime' },
    { key: 'roomName', label: 'Room' },
  ];

  private readonly scheduleService = inject(ScheduleService);
  private readonly scheduleDialog = inject(ScheduleDialogService);
  private readonly ticketDialog = inject(TicketDialogService);

  scheduleRowMenu: MenuItem[] = [
    COMMON_MENU_ITEMS.VIEW,
    { label: 'Book Ticket', icon: 'confirmation_number', action: 'book_ticket' },
  ];

  viewSchedule(scheduleId: string): void {
    this.hideSelf();

    this.scheduleDialog.displayInfo(scheduleId)
      .pipe(switchMap((res) => {
        if (!res) {
          this.onRefresh();
        } else if (res === 'edit') {
          return this.scheduleDialog.displayEdit(scheduleId);
        } else if (res === 'delete') {
          return this.scheduleDialog.displayDelete(() => this.scheduleService.delete(scheduleId));
        }
        return res;
      })).subscribe(() => this.onRefresh());
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

    this.ticketDialog.displayAdd({ scheduleId: scheduleId })
      .subscribe(() => this.onRefresh());
  }
}
