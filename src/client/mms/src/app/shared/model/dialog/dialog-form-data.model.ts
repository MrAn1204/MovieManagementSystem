import { FormGroup } from "@angular/forms";
import { DialogDataModel } from "./dialog-data.model";
import { BaseEntityModel } from "../base-entity.model";

export interface DialogFormDataModel<T extends BaseEntityModel> extends DialogDataModel<T> {
  form: FormGroup;
}
