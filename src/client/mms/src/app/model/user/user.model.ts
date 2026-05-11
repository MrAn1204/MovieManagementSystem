import { RoleModel } from './role.model';
import { AuditableEntityModel } from '../../shared/model/auditable-entity.model';

export interface UserModel extends AuditableEntityModel {
  username: string;
  fullname: string;
  dateOfBirth: string;
  email: string;
  phoneNumber: string;
  roles: RoleModel[];
}
