import { BaseEntityModel } from '../shared/model/base-entity.model';

export interface LanguageModel extends BaseEntityModel {
  name: string;
  nativeName: string;
}
