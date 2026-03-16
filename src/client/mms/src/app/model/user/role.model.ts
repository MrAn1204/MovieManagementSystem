import { BaseEntityModel } from '../../shared/model/base-entity.model';

export interface RoleModel extends BaseEntityModel {
  name: string;
  description: string;
}
