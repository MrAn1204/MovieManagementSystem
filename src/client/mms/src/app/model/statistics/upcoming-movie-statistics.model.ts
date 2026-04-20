import { BaseEntityModel } from '../../shared/model/base-entity.model';

export interface UpcomingMovieStatisticsModel extends BaseEntityModel {
  id: string;
  name: string;
  releaseDate: string;
}
