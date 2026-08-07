import { FormGroup } from "@angular/forms";
import { DialogDataModel } from "./dialog-data.model";
import { BaseEntityModel } from "../base-entity.model";
import { Observable } from "rxjs";

export interface DialogFormDataModel<T extends BaseEntityModel> extends DialogDataModel<T> {
  onSubmit: (form: FormGroup) => Observable<T>;
}
