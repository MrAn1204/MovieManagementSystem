import { Component, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NgxSpinnerModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  protected readonly title = signal('mms');

  constructor(private readonly spinner: NgxSpinnerService) {}

  ngOnInit(): void {
    this.spinner.show();

    setTimeout(() => {
      this.spinner.hide();
    }, 1000);
  }
}
