import { AuditableEntityModel } from '../../shared/model/auditable-entity.model';

export interface PromotionModel extends AuditableEntityModel {
  title: string;
  startDate: string;
  endDate: string;
  description: string;
  image: string;
  discount: number;
}
