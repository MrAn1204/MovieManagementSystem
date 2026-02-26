export interface MovieFormModel {
  name: string;
  releaseDate: string;
  duration: number;
  content: string;
  thumbnail: File | null;
  rating: number;
  genreIds: string[];
  studioIds: string[];
  talentIds: string[];
  languageId: string | null;
}
