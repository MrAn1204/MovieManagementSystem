import { SearchModel } from '../../shared/model/search.model';

export interface PromotionSearchModel extends SearchModel {
  startDate?: string;
  endDate?: string;
}
