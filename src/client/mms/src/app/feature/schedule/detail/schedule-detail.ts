import { Component, inject } from '@angular/core';
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { ScheduleModel } from '../../../model/schedule.model';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { BaseDialog } from '../../../shared/component/dialog/base/base-dialog';
import { DialogDataModel } from '../../../shared/model/dialog/dialog-data.model';
import { DIALOG_DATA } from '@angular/cdk/dialog';
import { Detail } from "../../../shared/component/detail/detail-component/detail";
import { ScheduleCreateEdit } from '../create-edit/schedule-create-edit';
import { PopupModal } from '../../../shared/component/dialog/popup-modal/popup-modal';

@Component({
  selector: 'app-schedule-detail',
  imports: [DetailText, FormatCellPipe, Detail],
  templateUrl: './schedule-detail.html',
  styleUrl: './schedule-detail.css',
})
export class ScheduleDetail extends BaseDialog {
  data: DialogDataModel<ScheduleModel> = inject(DIALOG_DATA);

  get model(): ScheduleModel | undefined {
    return this.data.model;
  }

  openUpdate(): void {
    this.dialogService.triggerOpen(ScheduleCreateEdit)
  }

  openDelete(): void {
    this.dialogService.triggerOpen(PopupModal);
  }
}
