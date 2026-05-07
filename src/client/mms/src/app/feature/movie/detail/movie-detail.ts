import { Component, inject } from '@angular/core';
import { DetailImage } from "../../../shared/component/detail/detail-image/detail-image";
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { Detail } from "../../../shared/component/detail/detail-component/detail";
import { MovieCreateEdit } from '../create-edit/movie-create-edit';
import { DetailDialog } from '../../../shared/component/dialog/detail/detail-dialog';
import { MovieDetailModel } from '../../../model/movie/movie-detail.model';
import { Table } from "../../../shared/component/table/table";
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { ScheduleSummaryModel } from '../../../model/schedule/schedule-summary.model';
import { Button } from "../../../shared/component/button/button";
import { DialogDataModel } from '../../../shared/model/dialog/dialog-data.model';
import { TicketModel } from '../../../model/ticket/ticket.model';
import { AuthService } from '../../../service/auth/auth.service';
import { EntityDialogService } from '../../../service/dialog/entity/entity-dialog.service';
import { TicketCreateEdit } from '../../ticket/create-edit/ticket-create-edit';
import { TicketService } from '../../../service/ticket/ticket.service';
import { SpinnerService } from '../../../service/ui/spinner/spinner.service';
import { finalize } from 'rxjs';
import { DialogPopupDataModel } from '../../../shared/model/dialog/dialog-popup-data.model';
import { FormMapper } from '../../../shared/util/form-mapper';
import { FormGroup } from '@angular/forms';
import { DialogRef } from '@angular/cdk/dialog';
import { BaseDialog } from '../../../shared/component/dialog/base/base-dialog';

@Component({
  selector: 'app-movie-detail',
  imports: [DetailImage, DetailText, FormatCellPipe, Detail, Table, Button],
  templateUrl: './movie-detail.html',
  styleUrl: './movie-detail.css',
})
export class MovieDetail extends DetailDialog<MovieDetailModel> {
  protected override updateDialog = MovieCreateEdit;

  private readonly authService = inject(AuthService);
  private readonly entityDialog = inject(EntityDialogService);
  private readonly ticketService = inject(TicketService);
  private readonly spinner = inject(SpinnerService);

  schedulesColumns: TableColumnModel<ScheduleSummaryModel>[] = [
    { key: 'showTime', label: 'Show Time', type: 'datetime' },
    { key: 'roomName', label: 'Room' },
  ];

  openBooking(scheduleId: string): void {
    const dialogData: DialogDataModel<TicketModel> = {
      title: 'Book Ticket',
      scheduleId: scheduleId,
      userId: this.authService.getId(),
    };

    const dialogRef = this.entityDialog.openForm(TicketCreateEdit, dialogData, (form) => this.handleBooking(form, dialogRef));
  }

  private handleBooking(form: FormGroup, dialogRef: DialogRef<unknown, BaseDialog>): void {
    const successDialogData: DialogPopupDataModel = {
      type: 'success',
      message: 'Ticket booked successfully',
    };

    this.spinner.show();

    this.ticketService.create(form.getRawValue())
      .pipe(finalize(() => this.spinner.hide()))
      .subscribe({
        next: () => {
          dialogRef.close()
          this.entityDialog.openPopup(successDialogData);
        },
        error: (error) => FormMapper.mapErrorResponse(error, form)
      });
  }
}
