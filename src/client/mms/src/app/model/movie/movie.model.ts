import { IdNameModel } from '../../shared/model/id-name.model';
import { AuditableEntityModel } from '../../shared/model/auditable-entity.model';

export interface MovieModel extends AuditableEntityModel {
  name: string;
  releaseDate: string;
  duration: number;
  rating: number;
  genres: IdNameModel[];
  studios: IdNameModel[];
  talents: IdNameModel[];
  language: IdNameModel;
}
