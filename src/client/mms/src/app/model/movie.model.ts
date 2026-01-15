import { IdNameModel } from '../shared/model/id-name.model';

export interface MovieModel {
	id: string;
	name: string;
	releaseDate: string;
	duration: number;
	content: string;
	thumbnail: string;
	rating: number;
	genres: IdNameModel[];
	studios: IdNameModel[];
	talents: IdNameModel[];
	language: IdNameModel;
}
