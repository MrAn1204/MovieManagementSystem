import { Component, computed, input, output } from '@angular/core';
import { MatButton, MatButtonAppearance } from "@angular/material/button";

@Component({
  selector: 'app-button-v2',
  imports: [MatButton],
  templateUrl: './button.html',
  styleUrl: './button.css',
})
export class ButtonV2 {
  variant = input<ButtonVariant>('simple');

  disabled = input<boolean>(false);

  clicked = output<MouseEvent>();

  appearance = computed(() => this.getAppearance(this.variant()));

  getAppearance(variant: ButtonVariant): MatButtonAppearance {
    switch (variant) {
      case 'primary':
      case 'danger':
      case 'success':
      case 'warning':
      case 'info':
        return 'filled';
      case 'secondary':
        return 'outlined';
      default:
        return 'text';
    }
  }

}

export type ButtonVariant = 'simple' | 'primary' | 'secondary' | 'danger' | 'success' | 'warning' | 'info';
