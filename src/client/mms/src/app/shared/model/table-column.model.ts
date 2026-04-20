import { BaseEntityModel } from './base-entity.model';

export interface TableColumnModel<T extends BaseEntityModel> {
  key: keyof T;
  label: string;
  type?: CellType;
  getValue?: (item: T) => any;
}

export type CellType = 'string' | 'number' | 'date' | 'time' | 'datetime' | 'array' | 'id-name' | 'id-name-array' | 'percentage';
