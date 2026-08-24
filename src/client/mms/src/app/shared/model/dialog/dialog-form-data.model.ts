import { DialogDataModel } from "./dialog-data.model";
import { BaseEntityModel } from "../base-entity.model";

export interface DialogFormDataModel<T extends BaseEntityModel> extends DialogDataModel<T> {
  id?: string;
}
