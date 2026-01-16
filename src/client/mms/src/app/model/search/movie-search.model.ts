import { SearchModel } from "../../shared/model/search.model";

export interface MovieSearchModel extends SearchModel {
  languageId?: string;
  genreIds?: string[];
  studioIds?: string[];
  releaseAfter?: string;
  releaseBefore?: string;
}