import { BaseEntityModel } from "../base-entity.model";
import { DialogDataModel } from "./dialog-data.model";

export interface DetailDialogDataModel<T extends BaseEntityModel> extends DialogDataModel<T> {
  openEdit?(): void;
  openDelete?(): void;
  id?: string;
  hasEdit?: boolean;
  hasDelete?: boolean;
}
