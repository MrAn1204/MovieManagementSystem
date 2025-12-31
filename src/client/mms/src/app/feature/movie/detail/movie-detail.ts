import { Component } from '@angular/core';
import { DetailImage } from "../../../shared/component/detail/detail-image/detail-image";
import { DetailText } from "../../../shared/component/detail/detail-text/detail-text";

@Component({
  selector: 'app-movie-detail',
  imports: [DetailImage, DetailText],
  templateUrl: './movie-detail.html',
  styleUrl: './movie-detail.css',
})
export class MovieDetail {

}
