import { BaseEntityModel } from '../shared/model/base-entity.model';

export interface GenreModel extends BaseEntityModel {
  name: string;
  description: string;
}
