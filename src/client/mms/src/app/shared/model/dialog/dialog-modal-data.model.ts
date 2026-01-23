import { DialogDataModel } from "./dialog-data.model";

export interface DialogModalDataModel extends DialogDataModel {
  type: 'info' | 'warning' | 'error' | 'success';
  message: string;
}