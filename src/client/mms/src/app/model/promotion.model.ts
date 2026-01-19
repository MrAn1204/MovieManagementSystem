import { BaseEntityModel } from '../shared/model/base-entity.model';

export interface PromotionModel extends BaseEntityModel {
  title: string;
  startDate: string;
  endDate: string;
  description: string;
  image: string;
  discount: number;
}
