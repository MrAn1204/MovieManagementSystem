import { Observable } from "rxjs";
import { BaseEntityModel } from "../shared/model/base-entity.model";

export interface DetailEntityService<T extends BaseEntityModel> {
  getById(id: string): Observable<T>;
  create(model: unknown): Observable<T>;
  update(id: string, entity: unknown): Observable<T>;
}
