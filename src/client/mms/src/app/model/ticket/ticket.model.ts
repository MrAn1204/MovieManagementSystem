import { IdNameModel } from '../../shared/model/id-name.model';
import { UserSummaryModel } from '../user/user-summary.model';
import { AuditableEntityModel } from '../../shared/model/auditable-entity.model';

export interface TicketModel extends AuditableEntityModel {
  name: string;
  movie: IdNameModel;
  user: UserSummaryModel;
  price: number;
  paid: boolean;
}
