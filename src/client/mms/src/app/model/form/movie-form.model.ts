import { MovieModel } from "../movie.model";

export class MovieFormModel {
  name: string;
  releaseDate: string;
  duration: number;
  content: string;
  thumbnail: string;
  rating: number;
  genreIds: string[];
  studioIds: string[];
  talentIds: string[];
  languageId: string | null;

  constructor(movie: MovieModel) {
    this.name = movie.name;
    this.releaseDate = movie.releaseDate;
    this.duration = movie.duration;
    this.content = movie.content;
    this.thumbnail = movie.thumbnail;
    this.rating = movie.rating;
    this.genreIds = movie.genres?.map(genre => genre.id) ?? [];
    this.studioIds = movie.studios?.map(studio => studio.id) ?? [];
    this.talentIds = movie.talents?.map(talent => talent.id) ?? [];
    this.languageId = movie.language?.id ?? null;
  }
}


