import { BaseEntityModel } from '../../shared/model/base-entity.model';
import { IdNameModel } from '../../shared/model/id-name.model';

export interface MovieModel extends BaseEntityModel {
  name: string;
  releaseDate: string;
  duration: number;
  rating: number;
  genres: IdNameModel[];
  studios: IdNameModel[];
  talents: IdNameModel[];
  language: IdNameModel;
}
