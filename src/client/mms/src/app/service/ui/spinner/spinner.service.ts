import { Injectable } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';

@Injectable({
  providedIn: 'root',
})
export class SpinnerService {
  constructor(private readonly spinner: NgxSpinnerService) { }

  show(): void {
    this.spinner.show();
  }

  hide(timeout: number = 500): void {
    setTimeout(() => this.spinner.hide(), timeout);
  }
}
