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
import { MatError } from '@angular/material/select';

@Component({
  selector: 'app-schedule-add-edit',
  imports: [AddEditContainer, FormInput, FormSelect, MatError],
  templateUrl: './schedule-add-edit.html',
  styleUrl: './schedule-add-edit.css',
})
export class ScheduleAddEdit extends AddEditDialog<ScheduleDetailModel> {
  protected override entityService: DetailEntityService<ScheduleDetailModel> = inject(ScheduleService);

  override form = this.formBuilder.nonNullable.group({
    showTime: [this.toDateTimeLocal(new Date())],
    movieId: ['', [CustomValidators.required('schedule.movie.required')]],
    roomId: ['', [CustomValidators.required('schedule.room.required')]],
  });

  movies = signal<FormOptionModel[]>([]);
  rooms = signal<FormOptionModel[]>([]);

  constructor(
    private readonly movieService: MovieService,
    private readonly roomService: RoomService,
  ) {
    super();
  }

  protected override mapForm(model: ScheduleDetailModel): void {
    this.form.patchValue({
      showTime: this.toDateTimeLocal(new Date(model.showTime)),
      movieId: model.movie.id,
      roomId: model.room.id,
    });
  }

  private toDateTimeLocal(date: Date): string {
    const pad = (n: number) => n.toString().padStart(2, '0');
    return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}T${pad(date.getHours())}:${pad(date.getMinutes())}`;
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
