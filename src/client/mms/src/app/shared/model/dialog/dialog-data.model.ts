import { Type } from "@angular/core";
import { RoleConfigModel } from "../role-config.model";

export interface DialogDataModel {
  title?: string;
  contentComponent?: Type<unknown>;
  contentInputs?: Record<string, unknown>;
  roleConfig?: RoleConfigModel;
}
