export interface NotificationDialogDataModel {
  type: NotificationType;
  message: string;
}

export type NotificationType = 'info' | 'success' | 'warning' | 'danger';
