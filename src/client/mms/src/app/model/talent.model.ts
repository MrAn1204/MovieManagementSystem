import { BaseEntityModel } from '../shared/model/base-entity.model';

export interface TalentModel extends BaseEntityModel {
  name: string;
  profile: string;
}
