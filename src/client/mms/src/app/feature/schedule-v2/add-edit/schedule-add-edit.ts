import { Component, inject, signal } from '@angular/core';
import { AddEditDialog } from '../../../shared/component-v2/dialog/add-edit-dialog/add-edit-dialog';
import { ScheduleDetailModel } from '../../../model/schedule/schedule-detail.model';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { MovieService } from '../../../service/movie/movie.service';
import { RoomService } from '../../../service/room/room.service';
import { AddEditContainer } from "../../../shared/component-v2/dialog/add-edit-container/add-edit-container";
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { FormSelect } from "../../../shared/component-v2/form/form-select/form-select";
import { DetailEntityService } from '../../../service/detail-entity.service';
import { ScheduleService } from '../../../service/schedule/schedule.service';

@Component({
  selector: 'app-schedule-add-edit',
  imports: [AddEditContainer, FormInput, FormSelect],
  templateUrl: './schedule-add-edit.html',
  styleUrl: './schedule-add-edit.css',
})
export class ScheduleAddEdit extends AddEditDialog<ScheduleDetailModel> {
  protected override entityService: DetailEntityService<ScheduleDetailModel> = inject(ScheduleService);

  override form = this.formBuilder.nonNullable.group({
      showTime: [this.model?.showTime ?? ''],
      movieId: [this.model?.movie?.id ?? null, [CustomValidators.required('schedule.movie.required')]],
      roomId: [this.model?.room?.id ?? null, [CustomValidators.required('schedule.room.required')]],
    });

  movies = signal<FormOptionModel[]>([]);
  rooms = signal<FormOptionModel[]>([]);

  constructor(
    private readonly movieService: MovieService,
    private readonly roomService: RoomService,
  ) {
    super();
  }

  override loadOptions(): void {
    this.movieService.getAll().subscribe((movies) => {
      this.movies.set(movies.map((movie) => ({
        label: movie.name,
        value: movie.id,
      })));
    });

    this.roomService.getAll().subscribe((rooms) => {
      this.rooms.set(rooms.map((room) => ({
        label: room.name,
        value: room.id,
      })));
    });
  }
}
