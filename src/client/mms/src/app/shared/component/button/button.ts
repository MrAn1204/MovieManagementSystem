import { Component, input, output } from '@angular/core';

export type ButtonVariant = 'primary' | 'secondary' | 'danger' | 'success' | 'warning' | 'icon' | 'simple';

@Component({
  selector: 'app-button',
  imports: [],
  host: {
    '[class.w-full]': 'variant() !== "icon"',
  },
  templateUrl: './button.html',
  styleUrl: './button.css',
})
export class Button {
  type = input<'button' | 'submit' | 'reset'>('button');
  variant = input<ButtonVariant>('primary');
  customStyles = input<string>('');
  disabled = input<boolean>(false);
  rounded = input<boolean>(true);

  clicked = output<MouseEvent>();

  handleClick(event: MouseEvent): void {
    this.clicked.emit(event);
  }

  get buttonClass(): string {
    const baseClass = 'button';
    const variantClass = this.variant();
    const customClass = this.customStyles().trim();
    const roundedClass = this.rounded() ? 'rounded' : '';

    return [baseClass, variantClass, customClass, roundedClass].join(' ');
  }
}
