import { BaseEntityModel } from "./base-entity.model";

export interface AuditableEntityModel extends BaseEntityModel {
  createdAt: string;
  updatedAt: string;
  createdBy: string;
  updatedBy: string;
}
