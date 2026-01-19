import { BaseEntityModel } from "./base-entity.model";

export interface PaginatedResult<T extends BaseEntityModel> {
  items: T[];
  itemCount: number;
  pageNumber: number;
  pageSize: number;
  pageCount: number;
}

export function createEmptyPaginatedResult<T extends BaseEntityModel>(): PaginatedResult<T> {
  return {
    items: [],
    itemCount: 0,
    pageNumber: 1,
    pageSize: 10,
    pageCount: 0,
  };
}