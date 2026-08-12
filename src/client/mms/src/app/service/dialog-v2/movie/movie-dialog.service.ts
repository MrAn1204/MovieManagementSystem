import { inject, Injectable, Type } from '@angular/core';
import { DialogServiceV2 } from '../dialog.service';
import { BaseDialogV2 } from '../../../shared/component-v2/dialog/base-dialog/base-dialog';
import { MovieDetailV2 } from '../../../feature/movie-v2/detail/movie-detail';
import { MovieAddEdit } from '../../../feature/movie-v2/add-edit/movie-add-edit';
import { EntityService } from '../../entity.service';
import { MovieModel } from '../../../model/movie/movie.model';
import { MovieService } from '../../movie/movie.service';

@Injectable({
  providedIn: 'root',
})
export class MovieDialogService extends DialogServiceV2<MovieModel> {
protected override entityName: string = 'Movie';

  protected override entityService: EntityService<MovieModel> = inject(MovieService);

  protected override detailDialog: Type<BaseDialogV2> = MovieDetailV2;

  protected override formDialog: Type<BaseDialogV2> = MovieAddEdit;
}
