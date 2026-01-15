export interface TableColumnModel {
  key: string;
  label: string;
  type?: 'string' | 'number' | 'date' | 'array' | 'id-name' | 'id-name-array';
}