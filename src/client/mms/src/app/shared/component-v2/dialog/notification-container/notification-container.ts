import { Component, computed, inject, input, output, signal } from '@angular/core';
import { ButtonV2 } from "../../button/button";
import { NotificationType } from '../../../model/dialog/notification-dialog-data.model';
import { DialogContainer } from "../dialog-container/dialog-container";
import { BreakpointObserver } from '@angular/cdk/layout';

@Component({
  selector: 'app-notification-container',
  imports: [ButtonV2, DialogContainer],
  templateUrl: './notification-container.html',
  styleUrl: './notification-container.css',
})
export class NotificationContainer {
  actionAlign = signal<'end' | 'center'>('end');

  type = input<NotificationType>();
  message = input<string>();

  closeDialog = output<void>();
  confirm = output<void>();

  title = computed<string>(() => {
    switch (this.type()) {
      case 'info':
        return 'Note';
      case 'success':
        return 'Success';
      case 'warning':
        return 'Warning';
      case 'danger':
        return 'Failed';
      default:
        return '';
    }
  });

  constructor() {
    const breakpointObserver = inject(BreakpointObserver);

    breakpointObserver.observe('(min-width: 640px)').subscribe(result => {
      this.actionAlign.set(result.matches ? 'end' : 'center');
    });
  }
}
