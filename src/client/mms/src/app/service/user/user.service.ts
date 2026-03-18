import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserModel } from '../../model/user/user.model';
import { UserDetailModel } from '../../model/user/user-detail.model';
import { UserFormModel } from '../../model/form/user-form.model';
import { UserSearchModel } from '../../model/search/user-search.model';
import { PaginatedResult } from '../../shared/model/paginated-result.model';
import { EntityService } from '../entity.service';

@Injectable({
  providedIn: 'root',
})
export class UserService extends EntityService<UserModel> {
  protected override baseUrl = 'http://localhost:8080/api/users';

  getAll(): Observable<UserModel[]> {
    return this.http.get<UserModel[]>(this.baseUrl);
  }

  getById(id: string): Observable<UserDetailModel> {
    return this.http.get<UserDetailModel>(`${this.baseUrl}/${id}`);
  }

  create(user: UserFormModel): Observable<UserDetailModel> {
    const payload = {
      username: user.username,
      fullname: user.fullname,
      password: user.password,
      confirmPassword: user.confirmPassword,
      gender: user.gender,
      dateOfBirth: user.dateOfBirth,
      email: user.email,
      citizenIdNumber: user.citizenIdNumber,
      phoneNumber: user.phoneNumber,
      address: user.address,
      roleIds: user.roleIds,
    };

    return this.http.post<UserDetailModel>(`${this.baseUrl}/create`, payload);
  }

  update(id: string, user: UserFormModel): Observable<UserDetailModel> {
    const payload: Record<string, unknown> = {
      fullname: user.fullname,
      gender: user.gender,
      dateOfBirth: user.dateOfBirth,
      email: user.email,
      citizenIdNumber: user.citizenIdNumber,
      phoneNumber: user.phoneNumber,
      address: user.address,
      score: user.score,
      roleIds: user.roleIds,
    };

    if (user.password || user.confirmPassword) {
      payload['password'] = user.password;
      payload['confirmPassword'] = user.confirmPassword;
    }

    return this.http.put<UserDetailModel>(`${this.baseUrl}/${id}`, payload);
  }

  delete(id: string): Observable<null> {
    return this.http.delete<null>(`${this.baseUrl}/${id}`);
  }

  override search(filter: UserSearchModel): Observable<PaginatedResult<UserModel>> {
    return this.http.post<PaginatedResult<UserModel>>(`${this.baseUrl}/search`, filter);
  }
}
