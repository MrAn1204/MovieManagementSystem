import { Component, inject } from '@angular/core';
import { DetailImage } from "../../../shared/component/detail/detail-image/detail-image";
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { DIALOG_DATA } from '@angular/cdk/dialog';
import { DialogDataModel } from '../../../shared/model/dialog/dialog-data.model';
import { Detail } from "../../../shared/component/detail/detail-component/detail";
import { BaseDialog } from '../../../shared/component/dialog/base/base-dialog';
import { MovieCreateEdit } from '../create-edit/movie-create-edit';
import { PopupModal } from '../../../shared/component/dialog/popup-modal/popup-modal';
import { MovieModel } from '../../../model/movie.model';

@Component({
  selector: 'app-movie-detail',
  imports: [DetailImage, DetailText, FormatCellPipe, Detail],
  templateUrl: './movie-detail.html',
  styleUrl: './movie-detail.css',
})
export class MovieDetail extends BaseDialog {
  data: DialogDataModel<MovieModel> = inject(DIALOG_DATA);

  get model(): MovieModel {
    return this.data.model as MovieModel;
  }

  openUpdate(): void {
    this.dialogService.triggerOpen(MovieCreateEdit)
  }

  openDelete(): void {
    this.dialogService.triggerOpen(PopupModal);
  }
}
