export interface PaginatedResult<T> {
  items: T[];
  totalItems: number;
  pageNumber: number;
  pageSize: number;
  totalPages: number;
}

export function createEmptyPaginatedResult<T>(): PaginatedResult<T> {
  return {
    items: [],
    totalItems: 0,
    pageNumber: 1,
    pageSize: 10,
    totalPages: 0,
  };
}