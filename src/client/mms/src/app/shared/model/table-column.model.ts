import { BaseEntityModel } from './base-entity.model';

export interface TableColumnModel<T extends BaseEntityModel> {
  key: keyof T;
  label: string;
  type?: 'string' | 'number' | 'date' | 'datetime' | 'array' | 'id-name' | 'id-name-array';
}
