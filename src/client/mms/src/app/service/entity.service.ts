import { Observable } from "rxjs";
import { PaginatedResult } from "../shared/model/paginated-result.model";
import { BaseEntityModel } from "../shared/model/base-entity.model";
import { inject } from "@angular/core";
import { HttpClient } from "@angular/common/http";

export abstract class EntityService<T extends BaseEntityModel> {
  protected abstract readonly baseUrl: string;

  protected readonly http = inject(HttpClient);

  abstract getAll(): Observable<T[]>;

  abstract getById(id: string): Observable<T>;

  abstract create(model: unknown): Observable<T>;

  abstract update(id: string, model: unknown): Observable<T>;

  abstract delete(id: string): Observable<null>;

  search?(filter: unknown): Observable<PaginatedResult<T>>;
}
