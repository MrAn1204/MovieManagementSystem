import { Observable } from "rxjs";
import { PaginatedResult } from "../shared/model/paginated-result.model";
import { BaseEntityModel } from "../shared/model/base-entity.model";
import { inject } from "@angular/core";
import { HttpClient } from "@angular/common/http";

export abstract class EntityService<T extends BaseEntityModel, U extends T = T> {
  protected abstract readonly baseUrl: string;

  protected readonly http = inject(HttpClient);

  abstract getAll(): Observable<T[]>;

  abstract getById(id: string): Observable<U>;

  abstract create(model: unknown): Observable<U>;

  abstract update(id: string, model: unknown): Observable<U>;

  abstract delete(id: string): Observable<null>;

  search?(filter: unknown): Observable<PaginatedResult<T>>;
}
