import { Component, inject } from '@angular/core';
import { DetailContainer } from "../../../shared/component-v2/dialog/detail-container/detail-container";
import { DetailImage } from "../../../shared/component/detail/detail-image/detail-image";
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { MovieDetailModel } from '../../../model/movie/movie-detail.model';
import { DetailDialogV2 } from '../../../shared/component-v2/dialog/detail-dialog/detail-dialog';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { Table } from "../../../shared/component/table/table";
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { ScheduleSummaryModel } from '../../../model/schedule/schedule-summary.model';
import { Button } from "../../../shared/component/button/button";
import { DialogDataModel } from '../../../shared/model/dialog/dialog-data.model';
import { TicketModel } from '../../../model/ticket/ticket.model';
import { TicketCreateEdit } from '../../ticket/create-edit/ticket-create-edit';
import { AuthService } from '../../../service/auth/auth.service';
import { EntityDialogService } from '../../../service/dialog/entity/entity-dialog.service';
import { TicketService } from '../../../service/ticket/ticket.service';
import { SpinnerService } from '../../../service/ui/spinner/spinner.service';
import { FormGroup } from '@angular/forms';
import { DialogRef } from '@angular/cdk/dialog';
import { BaseDialog } from '../../../shared/component/dialog/base/base-dialog';
import { DialogPopupDataModel } from '../../../shared/model/dialog/dialog-popup-data.model';
import { finalize } from 'rxjs';
import { FormMapper } from '../../../shared/util/form-mapper';

@Component({
  selector: 'app-movie-detail',
  imports: [DetailContainer, DetailImage, DetailText, FormatCellPipe, Table, Button],
  templateUrl: './movie-detail.html',
  styleUrl: './movie-detail.css',
})
export class MovieDetailV2 extends DetailDialogV2<MovieDetailModel> {
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
