import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RoleModel } from '../../model/user/role.model';
import { EntityService } from '../entity.service';

@Injectable({
  providedIn: 'root',
})
export class RoleService extends EntityService<RoleModel> {
  protected override baseUrl = 'http://localhost:8080/api/roles';

  getAll(): Observable<RoleModel[]> {
    return this.http.get<RoleModel[]>(this.baseUrl);
  }

  getById(_id: string): Observable<RoleModel> {
    throw new Error('Role getById is not supported.');
  }

  create(_model: unknown): Observable<RoleModel> {
    throw new Error('Role create is not supported.');
  }

  update(_id: string, _model: unknown): Observable<RoleModel> {
    throw new Error('Role update is not supported.');
  }

  delete(_id: string): Observable<null> {
    throw new Error('Role delete is not supported.');
  }
}
