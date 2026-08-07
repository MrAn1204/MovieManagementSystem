import { Component, output } from '@angular/core';
import { MatIconButton } from "@angular/material/button";

@Component({
  selector: 'app-button-icon',
  imports: [MatIconButton],
  templateUrl: './button-icon.html',
  styleUrl: './button-icon.css',
})
export class ButtonIcon {
  clicked = output<MouseEvent>();
}
