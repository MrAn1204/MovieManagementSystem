import { IdNameModel } from '../../shared/model/id-name.model';
import { BaseEntityModel } from '../../shared/model/base-entity.model';
import { UserSummaryModel } from '../user/user-summary.model';

export interface TicketModel extends BaseEntityModel {
  name: string;
  movie: IdNameModel;
  user: UserSummaryModel;
  price: number;
}
