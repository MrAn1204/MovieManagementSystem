import { BaseEntityModel } from "../base-entity.model";
import { RoleConfigModel } from "../role-config.model";

export interface DialogDataModel<T extends BaseEntityModel | null> {
  title?: string;
  model?: T;
  roleConfig?: RoleConfigModel;
  [key: string]: unknown;
}
