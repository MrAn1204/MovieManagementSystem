import { Injectable, Type } from '@angular/core';
import { BaseDialogV2 } from '../../../shared/component-v2/dialog/base-dialog/base-dialog';
import { MovieDetailV2 } from '../../../feature/movie-v2/detail/movie-detail';
import { MovieAddEdit } from '../../../feature/movie-v2/add-edit/movie-add-edit';
import { MovieModel } from '../../../model/movie/movie.model';
import { EntityDialogServiceV2 } from '../entity-dialog.service';

@Injectable({
  providedIn: 'root',
})
export class MovieDialogService extends EntityDialogServiceV2<MovieModel> {
  protected override entityName: string = 'Movie';
  protected override detailDialog: Type<BaseDialogV2> = MovieDetailV2;
  protected override formDialog: Type<BaseDialogV2> = MovieAddEdit;
}
