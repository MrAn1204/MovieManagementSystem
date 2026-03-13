import { Component, input } from '@angular/core';

@Component({
  selector: 'app-map-description',
  imports: [],
  templateUrl: './map-description.html',
  styleUrl: './map-description.css',
})
export class MapDescription {
  showType = input(false);
}
