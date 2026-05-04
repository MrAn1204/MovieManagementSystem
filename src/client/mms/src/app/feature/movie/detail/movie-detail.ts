import { Component } from '@angular/core';
import { DetailImage } from "../../../shared/component/detail/detail-image/detail-image";
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';
import { Detail } from "../../../shared/component/detail/detail-component/detail";
import { MovieCreateEdit } from '../create-edit/movie-create-edit';
import { DetailDialog } from '../../../shared/component/dialog/detail/detail-dialog';
import { MovieDetailModel } from '../../../model/movie/movie-detail.model';

@Component({
  selector: 'app-movie-detail',
  imports: [DetailImage, DetailText, FormatCellPipe, Detail],
  templateUrl: './movie-detail.html',
  styleUrl: './movie-detail.css',
})
export class MovieDetail extends DetailDialog<MovieDetailModel> {
  protected override updateDialog = MovieCreateEdit;
}
