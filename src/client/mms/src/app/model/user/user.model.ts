import { RoleModel } from './role.model';
import { BaseEntityModel } from '../../shared/model/base-entity.model';

export interface UserModel extends BaseEntityModel {
  username: string;
  fullname: string;
  dateOfBirth: string;
  email: string;
  phoneNumber: string;
  roles: RoleModel[];
}
