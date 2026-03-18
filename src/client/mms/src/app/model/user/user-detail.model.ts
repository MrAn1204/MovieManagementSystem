import { InvoiceModel } from '../invoice/invoice.model';
import { UserModel } from './user.model';

export interface UserDetailModel extends UserModel {
  gender: string;
  citizenIdNumber: string;
  address: string;
  score: number;
  invoices: InvoiceModel[];
}
