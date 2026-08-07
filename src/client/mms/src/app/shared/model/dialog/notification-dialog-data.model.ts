import { Observable } from "rxjs";

export interface NotificationDialogDataModel {
  type: NotificationType;
  message: string;
  onConfirm?: () => Observable<null>
}

export type NotificationType = 'info' | 'success' | 'warning' | 'danger';
