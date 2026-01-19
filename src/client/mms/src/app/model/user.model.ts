import { InvoiceModel } from './invoice.model';
import { RoleModel } from './role.model';
import { BaseEntityModel } from '../shared/model/base-entity.model';

export interface UserModel extends BaseEntityModel {
  username: string;
  fullname: string;
  gender: string;
  dateOfBirth: string;
  email: string;
  citizenIdNumber: string;
  phoneNumber: string;
  address: string;
  score: number;
  roles: RoleModel[];
  invoices: InvoiceModel[];
}
