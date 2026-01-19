export interface PaginatedResult<T> {
  items: T[];
  itemCount: number;
  pageNumber: number;
  pageSize: number;
  pageCount: number;
}

export function createEmptyPaginatedResult<T>(): PaginatedResult<T> {
  return {
    items: [],
    itemCount: 0,
    pageNumber: 1,
    pageSize: 10,
    pageCount: 0,
  };
}