import { FormGroup } from "@angular/forms";
import { DialogDataModel } from "./dialog-data.model";

export interface DialogFormDataModel extends DialogDataModel {
  form: FormGroup;
}