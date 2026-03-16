import { BaseEntityModel } from '../../shared/model/base-entity.model';
import { InvoiceModel } from '../invoice/invoice.model';
import { RoleModel } from './role.model';

export interface UserDetailModel extends BaseEntityModel {
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
