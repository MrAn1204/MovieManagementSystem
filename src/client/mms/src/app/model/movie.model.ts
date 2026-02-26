import { IdNameModel } from '../shared/model/id-name.model';
import { BaseEntityModel } from '../shared/model/base-entity.model';

export interface MovieModel extends BaseEntityModel {
  name: string;
  releaseDate: string;
  duration: number;
  content: string;
  thumbnail?: string;
  rating: number;
  genres?: IdNameModel[];
  studios?: IdNameModel[];
  talents?: IdNameModel[];
  language?: IdNameModel;
}
