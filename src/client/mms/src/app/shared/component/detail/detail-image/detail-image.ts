import { Component, input, OnInit, signal } from '@angular/core';

@Component({
  selector: 'app-detail-image',
  imports: [],
  templateUrl: './detail-image.html',
  styleUrl: './detail-image.css',
})
export class DetailImage implements OnInit {
  private readonly DEFAULT_SRC = 'https://dummyimage.com/300x400/dddddd/000000&text=No+Image';

  src = input<string | null>();

  displaySrc = signal(this.src());

  ngOnInit(): void {
    this.displaySrc.set(this.src());
  }

  onImageError() {
    this.displaySrc.set(this.DEFAULT_SRC);
  }
}
