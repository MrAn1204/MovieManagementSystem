import { SearchModel } from '../../shared/model/search.model';

export interface TicketSearchModel extends SearchModel {
  showTime?: string;
  movieId?: string;
  roomId?: string;
  promotionId?: string;
}
