import { Component, input } from '@angular/core';

@Component({
  selector: 'app-detail-image',
  imports: [],
  templateUrl: './detail-image.html',
  styleUrl: './detail-image.css',
})
export class DetailImage {
  src = input<string>('https://dummyimage.com/300x400/dddddd/000000&text=No+Image');
}
