import { IdNameModel } from '../../shared/model/id-name.model';
import { BaseEntityModel } from '../../shared/model/base-entity.model';

export interface TicketModel extends BaseEntityModel {
  name: string;
  movie: IdNameModel;
  username: string;
  phoneNumber: string;
}
