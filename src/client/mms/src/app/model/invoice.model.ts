import { BaseEntityModel } from '../shared/model/base-entity.model';

export interface InvoiceModel extends BaseEntityModel {
  totalMoney: number;
  addScore: number;
  useScore: number;
  discount: number;
  tickets: string[];
}
