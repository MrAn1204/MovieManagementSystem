import { SearchModel } from '../../shared/model/search.model';

export interface UserSearchModel extends SearchModel {
  roleId?: string;
}
