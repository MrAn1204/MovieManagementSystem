import { Component, input } from '@angular/core';
import { DetailImage } from "../../../shared/component/detail/detail-image/detail-image";
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";
import { MovieModel } from '../../../model/movie.model';
import { FormatCellPipe } from '../../../shared/pipe/format-cell/format-cell-pipe';

@Component({
  selector: 'app-movie-detail',
  imports: [DetailImage, DetailText, FormatCellPipe],
  templateUrl: './movie-detail.html',
  styleUrl: './movie-detail.css',
})
export class MovieDetail {
  model = input.required<MovieModel>();
}
