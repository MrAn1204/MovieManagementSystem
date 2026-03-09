import { DialogDataModel } from "./dialog-data.model";

export interface DialogPopupDataModel extends DialogDataModel<null> {
  type: 'info' | 'warning' | 'error' | 'success';
  message: string;
}
