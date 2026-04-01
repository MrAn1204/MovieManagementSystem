import { Component, OnInit, signal } from '@angular/core';
import { InputField } from "../../../shared/component/form/input/input-field";
import { SelectField } from "../../../shared/component/form/select/select-field";
import { ControlContainer, FormGroupDirective, ReactiveFormsModule } from '@angular/forms';
import { MovieService } from '../../../service/movie/movie.service';
import { RoomService } from '../../../service/room/room.service';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { CreateEdit } from "../../../shared/component/create-edit/create-edit";
import { CreateEditDialog } from '../../../shared/component/dialog/create-edit/create-edit-dialog';
import { ScheduleDetailModel } from '../../../model/schedule/schedule-detail.model';
import { ValidationError } from "../../../shared/component/form/error/validation-error";
import { CustomValidators } from '../../../shared/util/custom-validators';

@Component({
  selector: 'app-schedule-create-edit',
  imports: [InputField, SelectField, ReactiveFormsModule, CreateEdit, ValidationError],
  viewProviders: [{ provide: ControlContainer, useExisting: FormGroupDirective }],
  templateUrl: './schedule-create-edit.html',
  styleUrl: './schedule-create-edit.css',
})
export class ScheduleCreateEdit extends CreateEditDialog<ScheduleDetailModel> implements OnInit {
  override form = this.createForm();

  movies = signal<FormOptionModel[]>([]);
  rooms = signal<FormOptionModel[]>([]);

  constructor(
    private readonly movieService: MovieService,
    private readonly roomService: RoomService,
  ) {
    super();
  }

  ngOnInit(): void {
    this.patchForm();
    this.loadOptions();
  }

  override createForm() {
    return this.formBuilder.nonNullable.group({
      showTime: [''],
      movieId: ['', [CustomValidators.required('schedule.movie.required')]],
      roomId: ['', [CustomValidators.required('schedule.room.required')]],
    });
  }

  override patchForm(): void {
    const model = this.data.model;
    if (!model) {
      return;
    }

    this.form.patchValue({
      ...model,
      movieId: model.movie?.id ?? null,
      roomId: model.room?.id ?? null,
    });
  }

  private loadOptions(): void {
    const model = this.data.model;

    const modelMovie = model?.movie.id;
    const modelRoom = model?.room.id;

    this.movieService.getAll().subscribe((movies) => {
      this.movies.set(movies.map((movie) => ({
        label: movie.name,
        value: movie.id,
        selected: modelMovie === movie.id || false
      })));
    });

    this.roomService.getAll().subscribe((rooms) => {
      this.rooms.set(rooms.map((room) => ({
        label: room.name,
        value: room.id,
        selected: modelRoom === room.id || false
      })));
    });
  }
}
