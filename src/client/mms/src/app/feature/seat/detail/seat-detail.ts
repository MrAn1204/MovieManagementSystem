import { Component, inject } from '@angular/core';
import { BaseDialog } from '../../../shared/component/dialog/base/base-dialog';
import { Detail } from "../../../shared/component/detail/detail-component/detail";
import { DialogDataModel } from '../../../shared/model/dialog/dialog-data.model';
import { DIALOG_DATA } from '@angular/cdk/dialog';
import { SeatModel } from '../../../model/seat.model';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { SeatCreateEdit } from '../create-edit/seat-create-edit';
import { PopupModal } from '../../../shared/component/dialog/popup-modal/popup-modal';

@Component({
  selector: 'app-seat-detail',
  imports: [Detail, FormatCellPipe, DetailText],
  templateUrl: './seat-detail.html',
  styleUrl: './seat-detail.css',
})
export class SeatDetail extends BaseDialog {
  data: DialogDataModel<SeatModel> = inject(DIALOG_DATA);

  get model(): SeatModel {
    return this.data.model as SeatModel;
  }

  openUpdate(): void {
    this.dialogService.triggerOpen(SeatCreateEdit)
  }

  openDelete(): void {
    this.dialogService.triggerOpen(PopupModal);
  }
}
