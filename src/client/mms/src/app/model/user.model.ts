import { InvoiceModel } from './invoice.model';

export interface UserModel {
  id: string;
  username: string;
  fullname: string;
  gender: string;
  dateOfBirth: string;
  email: string;
  citizenIdNumber: string;
  phoneNumber: string;
  address: string;
  score: number;
  roles: string[];
  invoices: InvoiceModel[];
}
