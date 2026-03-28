import { Component, input, output } from '@angular/core';

export type ButtonVariant = 'primary' | 'secondary' | 'danger' | 'success' | 'warning' |'icon';

@Component({
  selector: 'app-button',
  imports: [],
  templateUrl: './button.html',
  styleUrl: './button.css',
})
export class Button {
  type = input<'button' | 'submit' | 'reset'>('button');
  variant = input<ButtonVariant>('primary');
  customStyles = input<string>('');
  disabled = input<boolean>(false);

  clicked = output<MouseEvent>();

  handleClick(event: MouseEvent): void {
    this.clicked.emit(event);
  }

  get buttonClass(): string {
    const baseClass = 'button';
    const variantClass = this.variant();
    const customClass = this.customStyles().trim();

    return [baseClass, variantClass, customClass].join(' ');
  }
}
