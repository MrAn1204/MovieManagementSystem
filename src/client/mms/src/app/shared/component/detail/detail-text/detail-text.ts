import { Component, input } from '@angular/core';

@Component({
  selector: 'app-detail-text',
  imports: [],
  templateUrl: './detail-text.html',
  styleUrl: './detail-text.css',
})
export class DetailText {
  label = input.required<string>();
}
